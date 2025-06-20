package com.spring.implementation.repository;

import com.spring.implementation.model.TenantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantStatusRepository extends JpaRepository<TenantStatus, Long> {}

