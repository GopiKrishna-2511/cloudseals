package com.spring.implementation.service;

import com.spring.implementation.model.UserPrincipal;
import com.spring.implementation.model.Users;
import com.spring.implementation.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        log.info("service register user:{}",user);
        return user;
    }

    public String verify(Users user) {
        log.info("service verify user:{}",user);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            log.info("verify user isAuthenticated:{}",authentication.isAuthenticated());
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public Users loadUserById(Integer id) throws UsernameNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }



}