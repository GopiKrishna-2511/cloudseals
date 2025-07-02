package com.spring.implementation.service;


import com.spring.implementation.model.AIAgent;
import com.spring.implementation.model.Users;
import com.spring.implementation.repository.AIAgentRepository;
import com.spring.implementation.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AIAgentService {

    private final AIAgentRepository repository;


    public AIAgent createAgent(AIAgent agent) {
        return repository.save(agent);
    }

    public List<AIAgent> getAllAgents() {
        return repository.findAll();
    }

    public Optional<AIAgent> getAgentById(Integer id) {
        return repository.findById(id);
    }

    public void deleteAgent(Integer id) {
        repository.deleteById(id);
    }

    public AIAgent updateAgent(AIAgent aiAgent) {
        AIAgent existing = repository.findById(aiAgent.getId())
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        existing.setName(aiAgent.getName());
        existing.setAgentType(aiAgent.getAgentType());
        existing.setStatus(aiAgent.getStatus());
        existing.setConfigJson(aiAgent.getConfigJson());
        return repository.save(existing);
    }
}