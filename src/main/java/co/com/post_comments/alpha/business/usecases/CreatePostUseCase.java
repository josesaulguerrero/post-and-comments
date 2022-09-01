package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.CreatePost;
import co.com.post_comments.alpha.domain.post.entities.root.Post;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.Title;
import co.com.post_comments.alpha.domain.post.values.identities.PostId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreatePostUseCase {
    private final DomainEventRepository eventRepository;
    private final EventBus eventBus;

    public Flux<DomainEvent> apply(Mono<CreatePost> command) {
        return command
                .map(c -> new Post(new PostId(), new Author(c.author()), new Title(c.title()), Date.parse(c.postedAt())))
                .flatMapIterable(AggregateEvent::getUncommittedChanges)
                .flatMap(event -> this.eventRepository.save(event).doOnNext(this.eventBus::publishDomainEvent));
    }
}
