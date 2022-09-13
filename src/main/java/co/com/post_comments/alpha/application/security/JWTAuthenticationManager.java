package co.com.post_comments.alpha.application.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof JWT)
                .cast(JWT.class)
                .flatMap(this::validate);
    }

    private Mono<Authentication> validate(JWT token) {
        String username = this.jwtService.getUsernameFromToken(token);
        return this.userService.findByUsername(username)
                .flatMap(user ->
                        this.jwtService.isValidJWT(token, (AppUser) user)
                                ? Mono.just(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()))
                                : Mono.error(new IllegalArgumentException("Invalid JWT."))
                );
    }
}
