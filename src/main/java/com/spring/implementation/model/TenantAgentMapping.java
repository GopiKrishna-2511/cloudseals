package com.spring.implementation.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Table(name = "Tenant_Agent_Mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class TenantAgentMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    private Organizations organizations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", nullable = false)
    private AgentCatalog agentCatalog;

    @Column(name = "tenant_specific_config_json", columnDefinition = "TEXT")
    private String tenantSpecificConfigJson;

    @Column(name = "deployed_version")
    private String deployedVersion;

    @Column(name = "deployment_status")
    private String deploymentStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}