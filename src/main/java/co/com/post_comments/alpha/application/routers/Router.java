package co.com.post_comments.alpha.application.routers;


import co.com.post_comments.alpha.business.usecases.AddCommentUseCase;
import co.com.post_comments.alpha.business.usecases.ChangeCommentContentUseCase;
import co.com.post_comments.alpha.business.usecases.CreatePostUseCase;
import co.com.post_comments.alpha.domain.post.commands.AddComment;
import co.com.post_comments.alpha.domain.post.commands.ChangeCommentContent;
import co.com.post_comments.alpha.domain.post.commands.CreatePost;
import co.com.sofka.domain.generic.DomainEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase useCase) {
        return route(
                POST("create/post").and(accept(MediaType.APPLICATION_JSON)),
                request ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromPublisher(
                                                useCase.apply(request.bodyToMono(CreatePost.class)),
                                                DomainEvent.class
                                        )
                                )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase useCase) {
        return route(
                POST("add/comment").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(
                                BodyInserters.fromPublisher(
                                        useCase.apply(request.bodyToMono(AddComment.class)),
                                        DomainEvent.class
                                )
                        )
        );
    }

    @Bean
    @PreAuthorize("hasAnyRole(\"ADMIN\")")
    public RouterFunction<ServerResponse> changeCommentContent(ChangeCommentContentUseCase useCase) {
        return route(
                PATCH("patch/comment/content").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(
                                BodyInserters.fromPublisher(
                                        useCase.apply(request.bodyToMono(ChangeCommentContent.class)),
                                        DomainEvent.class
                                )
                        )
        );
    }
}
