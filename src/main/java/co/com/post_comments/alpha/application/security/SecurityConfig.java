package co.com.post_comments.alpha.application.security;

import co.com.post_comments.alpha.application.security.utils.JWTAuthenticationManager;
import co.com.post_comments.alpha.application.security.utils.JWTConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain getWetFilterBean(
            ServerHttpSecurity http,
            JWTConverter converter,
            JWTAuthenticationManager authenticationManager
    ) {
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authenticationManager);
        jwtFilter.setServerAuthenticationConverter(converter);

        return http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                    .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                    .formLogin().disable()
                    .httpBasic().disable()
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
