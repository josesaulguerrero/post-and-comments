package co.com.post_comments.alpha.application.security;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<AppUser, String> {
    Mono<AppUser> findByUsername(String username);
}
