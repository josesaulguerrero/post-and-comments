package co.com.post_comments.alpha.domain.post.entities;

import co.com.post_comments.alpha.domain.post.identities.CommentId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.sofka.domain.generic.Entity;

public class Comment extends Entity<CommentId> {
    private final Author author;
    private Content content;
    private final Date createdAt;

    public Comment(Author author, Content content, Date createdAt) {
        super(new CommentId());
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(CommentId entityId, Author author, Content content, Date createdAt) {
        super(entityId);
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
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
