package co.com.post_comments.alpha.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event);
}
