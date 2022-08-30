package co.com.post_comments.alpha.domain.post.entities.root;

import co.com.post_comments.alpha.domain.post.entities.Comment;
import co.com.post_comments.alpha.domain.post.events.CommentAdded;
import co.com.post_comments.alpha.domain.post.events.PostCreated;
import co.com.sofka.domain.generic.EventChange;

import java.util.ArrayList;

public class PostEventListener extends EventChange {
    public PostEventListener(Post post) {
        super.apply((PostCreated event) -> {
            post.postedAt = event.postedAt();
            post.author = event.author();
            post.title = event.title();
            post.comments = new ArrayList<>();
        });

        super.apply((CommentAdded event) -> {
            Comment comment = new Comment(event.commentId(), event.author(), event.content(), event.postedAt());
            post.comments.add(comment);
        });
    }
}
