package co.com.post_comments.alpha.application.security.utils;

import co.com.post_comments.alpha.application.security.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .cast(UserDetails.class);
    }
}
