package co.com.post_comments.alpha.application.security.data;

import co.com.post_comments.alpha.application.security.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepository {
    private final static String COLLECTION = "users";
    private final ReactiveMongoTemplate mongoDBTemplate;
    public Mono<AppUser> save(AppUser user) {
        return this.mongoDBTemplate
                .save(user, COLLECTION);
    }

    public Mono<AppUser> findByUsername(String username) {
        log.info("The UserRepository is trying to find a user with username: " + username);
        return this.mongoDBTemplate
                .findOne(
                        new Query(Criteria.where("username").is(username)),
                        AppUser.class,
                        COLLECTION
                )
                .doOnError(error -> log.error("Failed to find user with the given username; " + error.getMessage()));
    }
}
