package com.spring.implementation.service;


import com.spring.implementation.model.AiAgent;
import com.spring.implementation.repository.AIAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AIAgentService {

    private final AIAgentRepository repository;


    public AiAgent createAgent(AiAgent agent) {
        return repository.save(agent);
    }

    public List<AiAgent> getAllAgents() {
        return repository.findAll();
    }

    public Optional<AiAgent> getAgentById(Integer id) {
        return repository.findById(id);
    }

    public void deleteAgent(Integer id) {
        repository.deleteById(id);
    }

    public AiAgent updateAgent(AiAgent aiAgent) {
        AiAgent existing = repository.findById(aiAgent.getId())
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        existing.setName(aiAgent.getName());
        existing.setAgentType(aiAgent.getAgentType());
        existing.setStatus(aiAgent.getStatus());
        existing.setConfigJson(aiAgent.getConfigJson());
        return repository.save(existing);
    }
}