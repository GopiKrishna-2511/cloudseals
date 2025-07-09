package com.spring.implementation.repository;


import com.spring.implementation.model.AiAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIAgentRepository extends JpaRepository<AiAgent, Integer> {
}