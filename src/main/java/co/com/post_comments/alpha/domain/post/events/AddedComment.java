package co.com.post_comments.alpha.domain.post.events;

import co.com.post_comments.alpha.domain.post.identities.CommentId;
import co.com.post_comments.alpha.domain.post.identities.PostId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.sofka.domain.generic.DomainEvent;

public class AddedComment extends DomainEvent {
    private final PostId postId;
    private final CommentId commentId;
    private final Author author;
    private final Content content;

    public AddedComment(PostId postId, CommentId commentId, Author author, Content content) {
        super("PostAndComments.posts.AddedComment");
        this.postId = postId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
    }

    public PostId postId() {
        return postId;
    }

    public CommentId commentId() {
        return commentId;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }
}
