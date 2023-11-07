# Organize your GraphQL Schema

I've been recently working with GraphQL, both in Node.js and Java with Apollo and Spring Boot respectively and for the latter, a top-down approach of contract-first is supported, which has always been my preferred way to implement service contracts.

Although it may seem a bit cumbersome because of the need to convert the GraphQL schema into code, it enables its 
modularization, so it's easier to maintain when changes are made in a repository.

In [Spring GraphQL](https://spring.io/projects/spring-graphql/), you can define your schema in your 
**resources/graphql** folder in a ***.graphqls** file, as 
simple as this:

```
📂 src
  📂 main
    📂 resources
      📂 graphql
        📄 schema.graphqls
```
- schema.graphqls
```GraphQL
type Query {
    echo(message: String): String
}
```

At first, it may seem that only one file ***.graphqls** is needed, but soon you'll notice that it will start to grow 
bigger as soon as you start adding more elements to it:

```GraphQL
type Query {
    echo(message: String): String
    findUsers(input: FindUsersInput!): [User]
}

input FindUsersInput {
    name: String
    email: String
}

type User {
    id: ID
    name: String
    email: String
}
```

And, because here in Spring GraphQL the ordered definition of schema elements is not needed, you can just keep adding things below...

At some point this **schema.graphqls** file can be affected by many commits all at once in your repository, and merge 
conflicts will arise as a consequence.

So, in order to avoid this, we can take advantage of GraphQL's extension mechanism, which basically allows you to define a type once and then extend it later, like this:

```GraphQL
type Query {
    echo(message: String): String
}

extend type Query {
    findUsers(input: FindUsersInput!): [User]
}

input FindUsersInput {
    name: String
    email: String
}

type User {
    id: ID
    name: String
    email: String
}
```

Now, Spring GraphQL also allows you to have as many ***.graphqls** files as you need... Even better, it also allows you to nest them with any folder structure. This will help us address the multiple commit conflicts.

In my experience, the best way to organize all your ***.graphqls** schema is by using one folder for each schema component:

```
📂 resources
  📂 graphql
    📂 extensions
      📂 Query
        📄 findAllUsers.graphqls
      📂 inputs
        📄 FindAllUsers.graphqls
      📂 types
        📄 Query.graphqls
        📄 User.graphqls
      📄 schema.graphqls
```

However, you need to keep a "root" schema.graphqls file at the base folder:

```GraphQL
schema {
    query: Query
}
```

Usually, you don't need to define the schema per-se, neither the Query type, but this is needed for the extension to work properly.

This means that you'll need to define at least one operation in it, for example:

- types/Query.graphqls
```GraphQL
type Query {
    echo(message: String): String
}
```
And then, you just need to separate the rest in their own file and folder:

- inputs/FindAllUsersInput.graphqls
```GraphQL
input FindAllUsersInput {
    name: String
    email: String
}
```
- types/User.graphqls
```GraphQL
type User {
    id: ID
    name: String
    email: String
}
```
- extensions/Query/findAllUsers.graphqls
```GraphQL
extend type Query {
    findUsers(input: FindUsersInput!): [User]
}
```
As you might image already, the same needs to be done for the Mutation type.

Finally, you might want to have specific sub-folders in the types folder, because, depending on your federation 
needs, you can either have common or specific subgraph types.

Obviously, best practices recommends to always have common types; however, in my experience, it's better to use a 
"wrapper" approach, similar to what SOAP web services used to have for operation's requests and responses:

- extensions/Query/findAllUsers.graphqls
```GraphQL
extend type Query {
findUsers(input: FindUsersInput!): FindUsersOutput!
}
```
- inputs/FindAllUsersInput.graphqls
```GraphQL
input FindAllUsersInput {
    name: String
    email: String
}
```
- types/FindAllUsersOutput.graphqls
```GraphQL
type FindAllUsersOutput {
    users: [Users]
}
```
- types/User.graphqls
```GraphQL
type User {
    id: ID
    name: String
    email: String
}
```
This will help to keep consistency for all operations you define.

---

Hope this helps you organize better you GraphQL schema in Spring Boot!

You can find the example code of this here: [https://github.com/LuisLandgrave/organize-your-graphql-schema](https://github.com/LuisLandgrave/organize-your-graphql-schema)