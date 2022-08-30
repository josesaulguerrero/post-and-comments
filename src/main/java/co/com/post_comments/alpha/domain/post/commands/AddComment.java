package co.com.post_comments.alpha.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddComment extends Command {
    private final String postId;
    private final String author;
    private final String content;
    private final String postedAt;

    public String postId() {
        return postId;
    }

    public String author() {
        return author;
    }

    public String content() {
        return content;
    }

    public String postedAt() {
        return postedAt;
    }
}
