package com.spring.implementation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name = "ai_agents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class AiAgents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    private Organizations organizations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private Users users;

    @Column(name = "name")
    private String name;

    @Column(name = "agent_type")
    private String agentType;

    @Column(name = "status")
    private String status;

    @Column(name = "config_json")
    private String configJson;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}