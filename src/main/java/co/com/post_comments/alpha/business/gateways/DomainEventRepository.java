package co.com.post_comments.alpha.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainEventRepository {
    Mono<DomainEvent> save(DomainEvent event);
    Flux<DomainEvent> findByAggregateId(String id);
}
