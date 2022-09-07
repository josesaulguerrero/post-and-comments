package co.com.post_comments.alpha.domain.post.entities.root;

import co.com.post_comments.alpha.domain.post.entities.Comment;
import co.com.post_comments.alpha.domain.post.events.CommentAdded;
import co.com.post_comments.alpha.domain.post.events.CommentContentChanged;
import co.com.post_comments.alpha.domain.post.events.PostCreated;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.identities.CommentId;
import co.com.sofka.domain.generic.EventChange;

import java.util.ArrayList;

public class PostEventListener extends EventChange {
    public PostEventListener(Post post) {
        super.apply((PostCreated event) -> {
            post.postedAt = event.postedAt();
            post.author = event.author();
            post.title = event.title();
            post.content = event.content();
            post.comments = new ArrayList<>();
        });

        super.apply((CommentAdded event) -> {
            Comment comment = new Comment(
                    new CommentId(event.commentId()),
                    new Author(event.author()),
                    new Content(event.content()),
                    new Date(event.postedAt())
            );
            post.comments.add(comment);
        });

        super.apply((CommentContentChanged event) -> {
            Comment comment = post.comments
                    .stream()
                    .filter(c -> c.identity().value().equals(event.commentId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The given CommentId is not associated to this Post or it doesn't exist."));
            comment.changeContent(new Content(
                    event.commentContent()
            ));
        });
    }
}
