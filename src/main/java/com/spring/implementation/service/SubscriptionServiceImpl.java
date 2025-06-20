package com.spring.implementation.service;
import com.spring.implementation.model.AgentInfo;
import com.spring.implementation.model.Subscription;
import com.spring.implementation.model.SubscriptionRequest;
import com.spring.implementation.model.TenantStatus;
import com.spring.implementation.repository.SubscriptionRepository;
import com.spring.implementation.repository.TenantStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    private TenantStatusRepository tenantRepo;

    @Override
    public String createSubscriptions(SubscriptionRequest request) {
        TenantStatus tenant = tenantRepo.findById(request.getTenantId())
            .orElseThrow(() -> new RuntimeException("Tenant not found"));

        List<Subscription> subs = request.getAgents().stream().map(agent -> {
            Subscription s = new Subscription();
            s.setAgentId(agent.getAgentId());
            s.setAgentName(agent.getAgentName());
            s.setSubscriptionId(request.getSubscriptionId());
            s.setTenant(tenant);
            return s;
        }).toList();

        subscriptionRepo.saveAll(subs);
        return "Subscriptions created";
    }

    @Override
    public String updateSubscriptions(SubscriptionRequest request) {
        List<Long> ids = request.getAgents().stream()
            .map(AgentInfo::getId)
            .filter(Objects::nonNull)
            .toList();

        List<Subscription> existing = subscriptionRepo.findAllByIdIn(ids);
        Map<Long, Subscription> map = existing.stream()
            .collect(Collectors.toMap(Subscription::getId, s -> s));

        for (AgentInfo agent : request.getAgents()) {
            Subscription sub = map.get(agent.getId());
            if (sub != null) {
                sub.setAgentId(agent.getAgentId());
                sub.setAgentName(agent.getAgentName());
            }
        }

        subscriptionRepo.saveAll(existing);
        return "Subscriptions updated";
    }

    @Override
    public String deleteSubscriptions(List<Long> ids) {
        subscriptionRepo.deleteAllById(ids);
        return "Subscriptions deleted";
    }

    @Override
    public List<SubscriptionRequest> getSubscriptions(List<Long> ids) {
        List<Subscription> subscriptions = subscriptionRepo.findAllById(ids);

        // Group by tenant ID
        Map<Long, List<Subscription>> groupedByTenant = subscriptions.stream()
                .collect(Collectors.groupingBy(sub -> sub.getTenant().getId()));

        return groupedByTenant.entrySet().stream().map(entry -> {
            Long tenantId = entry.getKey();
            List<AgentInfo> agents = entry.getValue().stream().map(sub -> {
                AgentInfo info = new AgentInfo();
                info.setId(sub.getId());
                info.setAgentId(sub.getAgentId());
                info.setAgentName(sub.getAgentName());
                return info;
            }).collect(Collectors.toList());

            SubscriptionRequest request = new SubscriptionRequest();
            request.setTenantId(tenantId);
            request.setAgents(agents);
            return request;
        }).collect(Collectors.toList());

    }
}