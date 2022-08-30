package co.com.post_comments.alpha.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentAdded extends DomainEvent {
    private String postId;
    private String commentId;
    private String author;
    private String content;
    private String postedAt;

    public CommentAdded() {
        super(CommentAdded.class.getName());
    }

    public CommentAdded(String postId, String commentId, String author, String content, String postedAt) {
        super(CommentAdded.class.getName());
        this.postId = postId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.postedAt = postedAt;
    }

    public String postId() {
        return postId;
    }

    public String commentId() {
        return commentId;
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
