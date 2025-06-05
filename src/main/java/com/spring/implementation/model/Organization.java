package com.spring.implementation.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String email;
    private String phone;
    private String address;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // Getters and Setters
    public Organization() {
    }

    public Organization(Long id, String name, String type, String email, String phone, String address, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
    }

    public Organization(long l, String companyA, String tech, String mail, String number, String addressA) {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}