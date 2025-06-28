package com.spring.implementation.repository;

import com.spring.implementation.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByIdIn(List<Long> ids);
}