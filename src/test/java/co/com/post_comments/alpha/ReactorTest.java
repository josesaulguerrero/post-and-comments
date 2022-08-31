package co.com.post_comments.alpha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class ReactorTest {
    @Test
    void test() {
        Mono.just(1)
                .flatMapMany(element -> Flux.just(1, 2, 3, 4))
                .doOnNext(System.out::println)
                .subscribe();
    }
}
