package com.user_api.controller;

import com.user_api.model.User;
import com.user_api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public List<User> listar() {
        return usersRepository.findAll();
    }

    @PostMapping("/users")
    public User adicionar(@RequestBody User user) {
        return usersRepository.save(user);
    }
}
