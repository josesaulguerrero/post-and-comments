package co.com.post_comments.alpha.domain.post.entities;

import co.com.post_comments.alpha.domain.post.values.identities.CommentId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.sofka.domain.generic.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Comment extends Entity<CommentId> {
    private final Author author;
    private Content content;
    private final Date postedAt;

    public Comment(Author author, Content content, Date postedAt) {
        super(new CommentId());
        this.author = author;
        this.content = content;
        this.postedAt = postedAt;
    }

    public Comment(CommentId entityId, Author author, Content content, Date postedAt) {
        super(entityId);
        this.author = author;
        this.content = content;
        this.postedAt = postedAt;
    }

    public void changeContent(Content content) {
        this.content = content;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }

    public Date postedAt() {
        return postedAt;
    }
}
