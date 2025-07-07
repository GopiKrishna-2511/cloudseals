package com.spring.implementation.controller;

import com.spring.implementation.model.AIAgent;
import com.spring.implementation.model.Organizations;
import com.spring.implementation.model.Users;
import com.spring.implementation.service.AIAgentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AIAgentControllerTest {

    @Mock
    private AIAgentService service;

    @InjectMocks
    private AIAgentController controller;

    private Organizations mockOrg() {
        return Organizations.builder()
            .id(1)
            .name("CloudCorp")
            .name("service")
            .createdAt(LocalDateTime.now())
            .build();
    }

    private Users mockUser() {
        return Users.builder()
            .id(1)
            .username("gopi.dev")
            .role("admin")
            .build();
    }

    private AIAgent buildAgent(Integer id, String name) {
        return AIAgent.builder()
            .id(id)
            .organizations(mockOrg())
            .name(name)
            .agentType("metrics")
            .status("active")
            .configJson("{\"level\":\"debug\"}")
            .createdBy(mockUser())
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    void testCreateAgent() {
        AIAgent request = buildAgent(null, "MetricsBot");
        AIAgent saved = buildAgent(1, "MetricsBot");

        when(service.createAgent(request)).thenReturn(saved);

        ResponseEntity<AIAgent> response = controller.create(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("MetricsBot", response.getBody().getName());
        assertEquals("metrics", response.getBody().getAgentType());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetAllAgents() {
        AIAgent agent1 = buildAgent(1, "GopiAI");
        AIAgent agent2 = buildAgent(2, "SecureAI");

        when(service.getAllAgents()).thenReturn(List.of(agent1, agent2));

        List<AIAgent> result = controller.getAll();

        assertEquals(2, result.size());
        assertEquals("GopiAI", result.get(0).getName());
        assertEquals("SecureAI", result.get(1).getName());
    }

    @Test
    void testGetAgentById_Found() {
        AIAgent agent = buildAgent(3, "AuditBot");

        when(service.getAgentById(3)).thenReturn(Optional.of(agent));

        ResponseEntity<AIAgent> response = controller.getById(3);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("AuditBot", response.getBody().getName());
    }

    @Test
    void testGetAgentById_NotFound() {
        when(service.getAgentById(99)).thenReturn(Optional.empty());

        ResponseEntity<AIAgent> response = controller.getById(99);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateAgent() {
        AIAgent updated = buildAgent(4, "UpdatedAI");

        when(service.updateAgent(updated)).thenReturn(updated);

        ResponseEntity<AIAgent> response = controller.update(updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UpdatedAI", response.getBody().getName());
    }

    @Test
    void testDeleteAgent() {
        doNothing().when(service).deleteAgent(5);

        ResponseEntity<Void> response = controller.delete(5);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}