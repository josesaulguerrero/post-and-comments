package co.com.post_comments.alpha.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CORSConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOriginPatterns(
                        "https://post-comments-c72b8.firebaseapp.com",
                        "https://post-comments-c72b8.web.app",
                        "http://localhost:[*]"
                )
                .allowedHeaders("*")
                .allowedMethods("POST")
                .maxAge(3600);
    }
}
