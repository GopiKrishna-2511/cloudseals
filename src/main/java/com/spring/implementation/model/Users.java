package com.spring.implementation.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;


@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Users {
    @Id
    private Integer id;
    private String username;
    private String password;
    @Column(name = "organization_id", updatable = false)
    private Long organizationId;
    private String role;
    private String email;
    private String status;
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

}