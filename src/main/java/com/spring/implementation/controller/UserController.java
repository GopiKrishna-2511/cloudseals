package com.spring.implementation.controller;

import com.spring.implementation.model.Users;
import com.spring.implementation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("cloudseal/v1/api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        log.info("register user: {}", user);
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        log.info("login user: {}", user);
        return service.verify(user);
    }

    @GetMapping("/user/{id}")
    public Users getUser(@PathVariable Integer id){
        log.info("get user: {}", id);
        return service.loadUserById(id);
    }

}