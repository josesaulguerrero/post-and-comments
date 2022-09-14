package co.com.post_comments.alpha.application.security;

import co.com.post_comments.alpha.application.security.filters.JWTAuthorizationFilter;
import co.com.post_comments.alpha.application.security.services.JWTService;
import co.com.post_comments.alpha.application.security.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain getWetFilterBean(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager authenticationManager,
            JWTService jwtService
    ) {
        return http
                .csrf().disable()
                .authorizeExchange()
                    .pathMatchers("/auth/**").permitAll()
                    .pathMatchers("/create/post", "/add/comment").hasRole("ADMIN")
                .and()
                    .addFilterAt(new JWTAuthorizationFilter(authenticationManager, jwtService), SecurityWebFiltersOrder.AUTHENTICATION)
                    //.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                    .formLogin().disable()
                    .httpBasic().disable()
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager getReactiveAuthenticationManager(PasswordEncoder passwordEncoder, UserService userService) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
