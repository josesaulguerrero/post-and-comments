package co.com.post_comments.alpha.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreatePost extends Command {
    private final String title;
    private final String author;
    private final String postedAt;

    public String title() {
        return title;
    }

    public String author() {
        return author;
    }

    public String postedAt() {
        return postedAt;
    }
}
