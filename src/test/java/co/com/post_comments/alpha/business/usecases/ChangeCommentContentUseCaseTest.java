package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.ChangeCommentContent;
import co.com.post_comments.alpha.domain.post.events.CommentAdded;
import co.com.post_comments.alpha.domain.post.events.CommentContentChanged;
import co.com.post_comments.alpha.domain.post.events.PostCreated;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ChangeCommentContentUseCaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private DomainEventRepository eventRepository;

    @InjectMocks
    private ChangeCommentContentUseCase useCase;

    @Test
    @DisplayName("ChangeCommentContentUseCase#apply should change a comment's content when successful.")
    void apply_ShouldChangeACommentContent_WhenSuccessful() {
        // Arrange
        ChangeCommentContent command = new ChangeCommentContent(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "New comment content"
        );

        CommentContentChanged event = new CommentContentChanged(
                command.postId(),
                command.commentId(),
                command.commentContent()
        );

        BDDMockito
                .when(this.eventRepository.findByAggregateId(ArgumentMatchers.anyString()))
                .thenReturn(Flux.just(
                        new PostCreated(
                                command.postId(),
                                "Test post",
                                "content",
                                "Tester",
                                LocalDateTime.now().minusDays(1).toString()
                        ),
                        new CommentAdded(
                                command.postId(),
                                command.commentId(),
                                "Tester",
                                "Comment content",
                                LocalDateTime.now().toString()
                        )
                ));

        BDDMockito
                .when(this.eventRepository.save(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(event));

        // Act
        Mono<List<DomainEvent>> triggeredEvents = this.useCase.apply(Mono.just(command))
                .collectList();

        // Assert
        StepVerifier.create(triggeredEvents)
                .expectSubscription()
                .expectNextMatches(events ->
                        events.size() == 1 &&
                        events.get(0) instanceof CommentContentChanged &&
                        ((CommentContentChanged) events.get(0)).commentContent().equals("New comment content")
                )
                .verifyComplete();

        BDDMockito.verify(this.eventBus, BDDMockito.times(1))
                .publishDomainEvent(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.eventRepository, BDDMockito.times(1))
                .save(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.eventRepository, BDDMockito.times(1))
                .findByAggregateId(ArgumentMatchers.anyString());
    }
}