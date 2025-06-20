package com.spring.implementation.controller;

import com.spring.implementation.model.SubscriptionRequest;
import com.spring.implementation.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cloudseal/v1/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(service.createSubscriptions(request));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(service.updateSubscriptions(request));
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<String> delete(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(service.deleteSubscriptions(ids));
    }
}