package com.example.demo.services;

import com.example.demo.graphql.User;
import com.example.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private UsersRepository repository;

    public UsersService(
        UsersRepository usersRepository
    ) {
        this.repository = usersRepository;
    }

    public List<User> findBy(
        String name, String email
    ) {
        return this.repository.findBy(name, email);
    }

}
