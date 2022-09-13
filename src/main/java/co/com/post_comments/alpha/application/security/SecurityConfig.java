package co.com.post_comments.alpha.application.security;

import co.com.post_comments.alpha.application.security.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain getWetFilterBean(
            ServerHttpSecurity http
    ) {
        return http
                .csrf().disable()
                .authorizeExchange()
                    .pathMatchers("/auth/**").permitAll()
                    .pathMatchers("/create/post", "/add/comment").hasRole("ADMIN")
                .and()
                    //.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                    .formLogin().disable()
                    .httpBasic().disable()
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager getReactiveAuthenticationManager(UserService userService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
