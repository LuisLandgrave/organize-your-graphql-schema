package com.example.demo.controllers;

import com.example.demo.graphql.FindAllUsersInput;
import com.example.demo.graphql.FindAllUsersOutput;
import com.example.demo.services.UsersService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {

    private UsersService service;

    public UsersController(
        UsersService usersService
    ) {
        this.service = usersService;
    }

    @QueryMapping
    public FindAllUsersOutput findAllUsers(
        @Argument
        FindAllUsersInput input
    ) {
        var name = input.getName();
        var email = input.getEmail();
        var users = this.service.findBy(name, email);
        return FindAllUsersOutput.builder().withUsers(users).build();
    }

}
