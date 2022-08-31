package co.com.post_comments.alpha.business.commons;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface UseCaseForCommand<T extends Command> extends Function<Mono<T>, Flux<DomainEvent>> {
    @Override
    Flux<DomainEvent> apply(Mono<T> var1);
}
