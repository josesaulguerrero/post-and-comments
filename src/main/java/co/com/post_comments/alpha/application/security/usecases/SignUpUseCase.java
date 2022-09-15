package co.com.post_comments.alpha.application.security.usecases;

import co.com.post_comments.alpha.application.security.data.UserRepository;
import co.com.post_comments.alpha.application.security.models.*;
import co.com.post_comments.alpha.application.security.utils.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SignUpUseCase {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    public Mono<AuthResponse> apply(Mono<AuthRequest> request) {
        return request
                .flatMap(r -> {
                    String encodedPassword = this.passwordEncoder.encode(r.getPassword());
                    AppUser user = new AppUser(r.getUsername(), encodedPassword, Roles.ROLE_USER);
                    return this.userRepository.save(user)
                            .flatMap(u -> {
                                JWT jwt = this.jwtService
                                        .generateJWTForUserWithCredentials(u.getUsername(), u.getAuthorities());
                                return Mono.just(
                                        new AuthResponse(jwt.getValue())
                                );
                            });
                });
    }
}
