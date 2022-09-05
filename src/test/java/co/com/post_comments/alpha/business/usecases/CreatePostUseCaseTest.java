package co.com.post_comments.alpha.business.usecases;

import co.com.post_comments.alpha.business.gateways.DomainEventRepository;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.post_comments.alpha.domain.post.commands.CreatePost;
import co.com.post_comments.alpha.domain.post.events.PostCreated;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private DomainEventRepository eventRepository;

    @InjectMocks
    private CreatePostUseCase useCase;

    @Test
    @DisplayName("The use case should save a Post and trigger an event of type PostCreated when successful.")
    void apply_ShouldCreateANewPost_WhenSuccessful() {
        // Arrange
        CreatePost command = new CreatePost(
                "Tester",
                "Test post",
                "content",
                LocalDateTime.now().toString()
        );
        PostCreated event = new PostCreated(
                UUID.randomUUID().toString(),
                command.title(),
                command.content(),
                command.author(),
                command.postedAt()
        );

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
                        events.get(0) instanceof PostCreated &&
                        ((PostCreated) events.get(0)).author().equals("Tester")
                )
                .verifyComplete();
        BDDMockito.verify(this.eventBus, BDDMockito.atMostOnce()).publishDomainEvent(ArgumentMatchers.any(DomainEvent.class));
        BDDMockito.verify(this.eventRepository, BDDMockito.atMostOnce()).save(ArgumentMatchers.any(DomainEvent.class));
    }
}