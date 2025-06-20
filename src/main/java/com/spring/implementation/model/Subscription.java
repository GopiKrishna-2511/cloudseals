package com.spring.implementation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agentId;
    private String agentName;
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantStatus tenant;
}