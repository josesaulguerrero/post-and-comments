package co.com.post_comments.alpha.domain.post.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Date implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    public static Date parse(String value) {
        return new Date(LocalDateTime.parse(value));
    }

    @Override
    public LocalDateTime value() {
        return this.value;
    }
}
