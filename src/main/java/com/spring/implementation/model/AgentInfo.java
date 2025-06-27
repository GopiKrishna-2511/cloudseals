package com.spring.implementation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentInfo {
    private Long id; // Optional for update/delete
    private String agentId;
    private String agentName;
}