package com.spring.implementation.service;

import com.spring.implementation.model.Organizations;
import com.spring.implementation.model.Users;
import com.spring.implementation.repository.OrganizationRepository;
import com.spring.implementation.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepo repo;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService service;

    private Organizations mockOrg() {
        return Organizations.builder()
                .id(101)
                .name("CloudCorp")
               // .type("service")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Users mockUser() {
        return Users.builder()
                .username("gopi.dev")
                .password("securePass")
                .role("admin")
                .organizations(mockOrg())
                .build();
    }

    @BeforeEach
    void clearRandomIds() {
        when(repo.existsById(anyInt())).thenReturn(false); // simplify unique ID check
    }

    @Test
    void testRegister_Success() {
        Users inputUser = mockUser();
        inputUser.setOrganizations(mockOrg());

        when(organizationRepository.findById(101L)).thenReturn(Optional.of(mockOrg()));
        when(repo.save(any(Users.class))).thenAnswer(inv -> inv.getArgument(0));

        Users result = service.register(inputUser);

        assertNotNull(result);
        assertEquals("gopi.dev", result.getUsername());
        assertTrue(result.getId() >= 0 && result.getId() < 999999);
        verify(repo).save(any(Users.class));
    }

   /* @Test
    void testRegister_OrgNotFound() {
        Users inputUser = mockUser();
        inputUser.setOrganizations(Organizations.builder().id(404).build());

        when(organizationRepository.findById(404L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.register(inputUser));
        assertEquals("Organization ID not found", ex.getMessage());
    }*/

   /* @Test
    void testVerify_Success() {
        Users user = mockUser();

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user.getUsername())).thenReturn("mock.jwt.token");

        String token = service.verify(user);

        assertEquals("mock.jwt.token", token);
    }

    @Test
    void testVerify_Failure() {
        Users user = mockUser();

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        String result = service.verify(user);

        assertEquals("fail", result);
    }

    @Test
    void testLoadUserById_Found() {
        Users user = mockUser();
        user.setId(2);

        when(repo.findById(2)).thenReturn(Optional.of(user));

        ResponseEntity<Users> response = service.loadUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("gopi.dev", response.getBody().getUsername());
    }

    @Test
    void testLoadUserById_NotFound() {
        when(repo.findById(404)).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserById(404)
        );

        assertEquals("User not found with id: 404", ex.getMessage());
    }*/
}