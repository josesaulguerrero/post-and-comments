package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.AddComment;
import co.com.post_comments.alpha.domain.post.events.CommentAdded;
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
class AddCommentUseCaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private DomainEventRepository eventRepository;

    @InjectMocks
    private AddCommentUseCase useCase;

    @Test
    @DisplayName("AddCommentUseCase#apply should create a new comment and attach it to the corresponding Post when successful.")
    void apply_ShouldCreateANewComment_WhenSuccessful() {
        // Arrange
        AddComment command = new AddComment(
                UUID.randomUUID().toString(),
                "Tester comment",
                "This is a test comment",
                LocalDateTime.now().toString()
        );

        CommentAdded event = new CommentAdded(
                command.postId(),
                UUID.randomUUID().toString(),
                command.author(),
                command.content(),
                command.postedAt()
        );

        BDDMockito
                .when(this.eventRepository.findByAggregateId(ArgumentMatchers.anyString()))
                .thenReturn(Flux.just(
                        new PostCreated(
                                command.postId(),
                                "Test post",
                                content, "Tester",
                                LocalDateTime.now().minusDays(1).toString()
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
                        events.get(0) instanceof CommentAdded
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