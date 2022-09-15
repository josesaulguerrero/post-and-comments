package co.com.post_comments.alpha.application.security.routes;

import co.com.post_comments.alpha.application.security.models.AuthRequest;
import co.com.post_comments.alpha.application.security.models.AuthResponse;
import co.com.post_comments.alpha.application.security.usecases.LoginUseCase;
import co.com.post_comments.alpha.application.security.usecases.SignUpUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> logIn(LoginUseCase useCase) {
        return route(
                POST("auth/login").and(accept(MediaType.APPLICATION_JSON)),
                request ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromPublisher(
                                                useCase.apply(request.bodyToMono(AuthRequest.class)),
                                                AuthResponse.class
                                        )
                                )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> signUp(SignUpUseCase useCase) {
        return route(
                POST("auth/signup").and(accept(MediaType.APPLICATION_JSON)),
                request ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromPublisher(
                                                useCase.apply(request.bodyToMono(AuthRequest.class)),
                                                AuthResponse.class
                                        )
                                )
        );
    }
}
