package com.spring.implementation.service;

import com.spring.implementation.model.AIAgent;
import com.spring.implementation.model.Organizations;
import com.spring.implementation.model.Users;
import com.spring.implementation.repository.AIAgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AIAgentServiceTest {

    @Mock
    private AIAgentRepository repository;

    @InjectMocks
    private AIAgentService service;

    private Organizations mockOrg() {
        return Organizations.builder()
                .id(1)
                .name("CloudCorp")
               // .type("service")
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
        AIAgent request = buildAgent(null, "AuditBot");
        AIAgent saved = buildAgent(1, "AuditBot");

        when(repository.save(request)).thenReturn(saved);

        AIAgent result = service.createAgent(request);

        assertNotNull(result);
        assertEquals("AuditBot", result.getName());
        assertEquals(1, result.getId());
    }

    @Test
    void testGetAllAgents() {
        AIAgent a1 = buildAgent(1, "BotA");
        AIAgent a2 = buildAgent(2, "BotB");

        when(repository.findAll()).thenReturn(List.of(a1, a2));

        List<AIAgent> result = service.getAllAgents();

        assertEquals(2, result.size());
        assertEquals("BotA", result.get(0).getName());
    }

    @Test
    void testGetAgentById_Found() {
        AIAgent agent = buildAgent(3, "VisionBot");
        when(repository.findById(3)).thenReturn(Optional.of(agent));

        Optional<AIAgent> result = service.getAgentById(3);

        assertTrue(result.isPresent());
        assertEquals("VisionBot", result.get().getName());
    }

    @Test
    void testGetAgentById_NotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Optional<AIAgent> result = service.getAgentById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteAgent() {
        doNothing().when(repository).deleteById(7);

        service.deleteAgent(7);

        verify(repository).deleteById(7);
    }

    @Test
    void testUpdateAgent_Success() {
        AIAgent existing = buildAgent(4, "LegacyBot");
        AIAgent updated = buildAgent(4, "ModernBot");
        updated.setAgentType("AI");
        updated.setStatus("inactive");
        updated.setConfigJson("{\"trace\":true}");

        when(repository.findById(4)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        AIAgent result = service.updateAgent(updated);

        assertEquals("ModernBot", result.getName());
        assertEquals("AI", result.getAgentType());
        assertEquals("inactive", result.getStatus());
    }

    @Test
    void testUpdateAgent_NotFound() {
        AIAgent updated = buildAgent(5, "GhostBot");

        when(repository.findById(5)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateAgent(updated));
        assertEquals("Agent not found", ex.getMessage());
    }
}