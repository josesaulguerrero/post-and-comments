package co.com.post_comments.alpha.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {
    void publishDomainEvent(DomainEvent event);
    void publishError(Throwable error);
}
