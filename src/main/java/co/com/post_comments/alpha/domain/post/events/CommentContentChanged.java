package co.com.post_comments.alpha.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentContentChanged extends DomainEvent {
    private String postId;
    private String commentId;
    private String commentContent;

    public CommentContentChanged(String postId, String commentId, String commentContent) {
        super(CommentContentChanged.class.getName());
        this.postId = postId;
        this.commentId = commentId;
        this.commentContent = commentContent;
    }

    public CommentContentChanged() {
        super(CommentContentChanged.class.getName());
    }

    public String postId() {
        return postId;
    }

    public String commentId() {
        return commentId;
    }

    public String commentContent() {
        return commentContent;
    }
}
