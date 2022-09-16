package co.com.post_comments.alpha.application.adapters.repository;

import co.com.post_comments.alpha.application.commons.json.JSONMapper;
import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@Slf4j
@Repository
@AllArgsConstructor
public class MongoEventStoreRepository implements DomainEventRepository {

    private final ReactiveMongoTemplate mongoTemplate;
    private final JSONMapper jsonMapper;

    @Override
    public Flux<DomainEvent> findByAggregateId(String aggregateId) {
        log.info("The aggregate with the following id is being searched for: " + aggregateId);
        Query query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return mongoTemplate.find(query, EventDocument.class, "events")
                .sort(Comparator.comparing(EventDocument::timestamp))
                .map(event -> {
                    try {
                        return (DomainEvent) jsonMapper.readFromJson(event.eventJSON(), Class.forName(event.eventType()));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(error -> log.error("Something went wrong while finding the aggregate with the given id: " + error.getMessage()));
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        log.info("Saving an event with the following information: " + event.toString());
        return Mono.just(event)
                .map(domainEvent ->
                        new EventDocument(
                                domainEvent.aggregateRootId(),
                                this.jsonMapper.writeToJson(domainEvent),
                                LocalDateTime.now(),
                                domainEvent.getClass().getName()
                        )
                )
                .flatMap(document ->
                        this.mongoTemplate.save(document, "events")
                                .map(documentFromDB -> {
                                    try {
                                        return (DomainEvent) this.jsonMapper.readFromJson(documentFromDB.eventJSON(), Class.forName(documentFromDB.eventType()));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                        throw new IllegalStateException("Couldn't cast the given JSON string to any Domain Event.");
                                    }
                                })
                )
                .doOnError(error -> log.error("Something went wrong while trying to save the given event"));
    }
}
