package co.com.post_comments.alpha.application.security.filters;

import co.com.post_comments.alpha.application.security.models.JWT;
import co.com.post_comments.alpha.application.security.utils.JWTService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class JWTAuthorizationFilter extends AuthenticationWebFilter {
    private final JWTService jwtService;

    public JWTAuthorizationFilter(ReactiveAuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().contains("auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.trim().isEmpty()) {
            throw new IllegalArgumentException("The auth header cannot be empty or null");
        }
        JWT token = new JWT(this.getTokenFromAuthHeader(authHeader));
        String username = this.jwtService.getUsernameFromToken(token);
        Collection<? extends GrantedAuthority> authorities = this.jwtService.getAuthoritiesFromToken(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, token.getValue(),authorities);
        return chain
                .filter(exchange)
                .contextWrite(
                        ReactiveSecurityContextHolder.withAuthentication(authentication)
                );
    }

    private String getTokenFromAuthHeader(String authHeader) {
        if (!authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("The given auth token does not have a valid format.");
        }
        return authHeader.replaceAll("Bearer ", "");
    }
}
