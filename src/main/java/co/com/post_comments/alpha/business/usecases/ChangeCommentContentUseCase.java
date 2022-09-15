package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.commons.UseCaseForCommand;
import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.ChangeCommentContent;
import co.com.post_comments.alpha.domain.post.entities.root.Post;
import co.com.post_comments.alpha.domain.post.values.Content;
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
public class ChangeCommentContentUseCase implements UseCaseForCommand<ChangeCommentContent> {
    private final DomainEventRepository eventRepository;
    private final EventBus eventBus;

    @Override
    public Flux<DomainEvent> apply(Mono<ChangeCommentContent> command) {
        return command
                .flatMapMany(c ->
                        this.eventRepository.findByAggregateId(c.postId())
                                .collectList()
                                .map(events -> {
                                    Post post = Post.from(
                                            new PostId(c.postId()),
                                            events
                                    );
                                    post.changeCommentContent(
                                            post.identity(),
                                            new CommentId(c.commentId()),
                                            new Content(c.commentContent())
                                    );
                                    return post;
                                })
                                .flatMapIterable(AggregateEvent::getUncommittedChanges))
                .flatMap(event -> this.eventRepository.save(event).doOnNext(this.eventBus::publishDomainEvent));
    }
}
