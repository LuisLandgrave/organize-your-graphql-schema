package com.example.demo.repositories;

import com.example.demo.graphql.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository {

    private List<User> users = new ArrayList<>();

    public UsersRepository() {
        users.add(
            User.builder().withName("JohnDoe_123").withEmail("john-doe_123@example.com").build());
        users.add(User.builder()
                      .withName("alice_liddell")
                      .withEmail("downtherabbithole@example.com")
                      .build());
        users.add(User.builder()
                      .withName("bobbyTables")
                      .withEmail("sql_injection_ftw@example.com")
                      .build());
    }

    public List<User> findBy(
        String name, String email
    ) {
        List<User> result = new ArrayList<>(this.users);
        if (name != null) {
            result = result.stream().filter(user -> name.equals(user.getName())).toList();
        }
        if (email != null) {
            result = result.stream().filter(user -> email.equals(user.getEmail())).toList();
        }
        return result;
    }

}
