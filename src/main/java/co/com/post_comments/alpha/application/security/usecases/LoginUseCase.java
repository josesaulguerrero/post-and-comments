package co.com.post_comments.alpha.application.security.usecases;

import co.com.post_comments.alpha.application.security.data.UserRepository;
import co.com.post_comments.alpha.application.security.models.AuthRequest;
import co.com.post_comments.alpha.application.security.models.AuthResponse;
import co.com.post_comments.alpha.application.security.models.JWT;
import co.com.post_comments.alpha.application.security.utils.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class LoginUseCase {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    public Mono<AuthResponse> apply(Mono<AuthRequest> request) {
        return request
                .flatMap(
                        r -> this.userRepository.findByUsername(r.getUsername())
                                .map(user -> {
                                    if (!this.passwordEncoder.matches(r.getPassword(), user.getPassword())) {
                                        throw new IllegalArgumentException("The given credentials do not match.");
                                    }
                                    JWT jwt = this.jwtService.generateJWTForUserWithCredentials(user.getUsername(), user.getAuthorities());
                                    return new AuthResponse(jwt.getValue());
                                })
                );
    }
}
