package com.spring.implementation.repository;


import com.spring.implementation.model.AIAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIAgentRepository extends JpaRepository<AIAgent, Integer> {
}