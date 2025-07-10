package com.spring.implementation.service;

import com.spring.implementation.model.Organizations;
import com.spring.implementation.model.UserPrincipal;
import com.spring.implementation.model.Users;
import com.spring.implementation.repository.OrganizationRepository;
import com.spring.implementation.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private  final JWTService jwtService;
    private  final  AuthenticationManager authManager;
    private  final  UserRepo repo;
    private  final OrganizationRepository organizationRepository;
    private final Random random = new Random();


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {

        Organizations org = organizationRepository.findById(
                        user.getOrganizations().getId().longValue())
                .orElseThrow(() -> new RuntimeException("Organization ID not found"));

        int randomId;
        user.setPassword(encoder.encode(user.getPassword()));
        do {
            randomId = random.nextInt(999999); // example: random int between 0 and 999999
        } while (repo.existsById(randomId)); // ensure uniqueness

      //  user.setId(randomId);

        user.setOrganizations(org);
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

    public ResponseEntity<Users> loadUserById(Integer id) throws UsernameNotFoundException {
        return ResponseEntity.ok(repo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id)));
    }



}