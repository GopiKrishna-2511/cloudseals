package com.spring.implementation.service;

import com.spring.implementation.model.SubscriptionRequest;

import java.util.List;

public interface SubscriptionService {
    String createSubscriptions(SubscriptionRequest request);
    String updateSubscriptions(SubscriptionRequest request);
    String deleteSubscriptions(List<Long> ids);
    List<SubscriptionRequest> getSubscriptions(List<Long> ids);
}