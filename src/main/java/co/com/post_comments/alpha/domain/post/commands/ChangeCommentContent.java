package co.com.post_comments.alpha.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.*;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangeCommentContent extends Command {
    private String postId;
    private String commentId;
    private String commentContent;

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
