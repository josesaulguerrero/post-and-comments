package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.commons.UseCaseForCommand;
import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.AddComment;
import co.com.post_comments.alpha.domain.post.entities.root.Post;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.identities.CommentId;
import co.com.post_comments.alpha.domain.post.values.identities.PostId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AddCommentUseCase implements UseCaseForCommand<AddComment> {
    private final DomainEventRepository eventRepository;
    private final EventBus eventBus;

    @Override
    public Flux<DomainEvent> apply(Mono<AddComment> command) {
        return command
                .flatMapMany(c ->
                        this.eventRepository.findByAggregateId(c.postId())
                                .collectList()
                                .map(events -> {
                                    Post post = Post.from(
                                            new PostId(c.postId()),
                                            events
                                    );
                                    post.addComment(
                                            new PostId(c.postId()),
                                            new CommentId(),
                                            new Author(c.author()),
                                            new Content(c.content()),
                                            new Date(c.postedAt())
                                    );
                                    return post;
                                })
                                .flatMapIterable(AggregateEvent::getUncommittedChanges))
                .flatMap(event -> this.eventRepository.save(event).doOnNext(this.eventBus::publishDomainEvent));
    }
}
