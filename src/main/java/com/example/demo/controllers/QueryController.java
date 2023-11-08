package com.example.demo.controllers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryController {

    @QueryMapping
    public String echo(
        @Argument
        String message
    ) {
        return String.format("[%s]", message);
    }

}
