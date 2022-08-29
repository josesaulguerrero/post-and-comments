package co.com.post_comments.alpha.domain.post.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Author implements ValueObject<String> {
    private final String fullName;

    @Override
    public String value() {
        return this.fullName;
    }
}