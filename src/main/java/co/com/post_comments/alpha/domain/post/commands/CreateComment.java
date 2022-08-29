package co.com.post_comments.alpha.domain.post.commands;

import co.com.post_comments.alpha.domain.post.identities.PostId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateComment extends Command {
    private final PostId postId;
    private final Author author;
    private final Content content;

    private final Date createdAt;

    public PostId postId() {
        return postId;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }

    public Date createdAt() {
        return createdAt;
    }
}
