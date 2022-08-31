package co.com.post_comments.alpha.application.adapters.repository;

import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@Repository
@AllArgsConstructor
public class MongoEventStoreRepository implements DomainEventRepository {

    private final ReactiveMongoTemplate mongoTemplate;
    private final ObjectMapper jsonMapper;

    @Override
    public Flux<DomainEvent> findByAggregateId(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return mongoTemplate.find(query, EventDocument.class, "alpha-events")
                .sort(Comparator.comparing(EventDocument::timestamp))
                .map(event -> {
                    try {
                        return (DomainEvent) jsonMapper.readValue(event.json(), Class.forName(event.eventType()));
                    } catch (ClassNotFoundException | JsonProcessingException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("Couldn't cast the given JSON string to any Domain Event.");
                    }
                });
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        return Mono.just(event)
                .map(domainEvent -> {
                    try {
                        return new EventDocument(
                                domainEvent.aggregateRootId(),
                                this.jsonMapper.writeValueAsString(domainEvent),
                                LocalDateTime.now(),
                                domainEvent.getClass().getName()
                        );
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(document -> {
                    try {
                        this.mongoTemplate.save(document, "alpha-events");
                        return (DomainEvent) jsonMapper.readValue(document.json(), Class.forName(document.eventType()));
                    } catch (ClassNotFoundException | JsonProcessingException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("Couldn't cast the given JSON string to any Domain Event.");
                    }
                });
    }
}
