package co.com.post_comments.alpha.domain.post.events;

import co.com.post_comments.alpha.domain.post.identities.PostId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.Title;
import co.com.sofka.domain.generic.DomainEvent;

public class CreatedPost extends DomainEvent {
    private final PostId postId;
    private final Title title;
    private final Author author;
    private final Date createdAt;

    public CreatedPost(PostId postId, Title title, Author author, Date createdAt) {
        super("PostAndComments.posts.CreatedPost");
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
    }

    public PostId postId() {
        return postId;
    }

    public Title title() {
        return title;
    }

    public Author author() {
        return author;
    }

    public Date createdAt() {
        return createdAt;
    }
}
