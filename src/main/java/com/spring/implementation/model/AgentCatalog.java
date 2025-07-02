package com.spring.implementation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catalogId;

    @Column(nullable = false, length = 100)
    private String agentName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentType agentType;

    @Column(nullable = false, length = 50)
    private String version;

    @Column(columnDefinition = "json", nullable = false)
    private String configJson;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime lastUpdatedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('active','inactive')", nullable = false)
    private AgentStatus status;

    public enum AgentType { DevOps, FinOps, SecOps }
    public enum AgentStatus { active, inactive }
}