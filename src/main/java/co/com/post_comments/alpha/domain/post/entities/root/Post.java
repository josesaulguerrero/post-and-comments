package co.com.post_comments.alpha.domain.post.entities.root;

import co.com.post_comments.alpha.domain.post.events.AddedComment;
import co.com.post_comments.alpha.domain.post.events.CreatedPost;
import co.com.post_comments.alpha.domain.post.identities.CommentId;
import co.com.post_comments.alpha.domain.post.identities.PostId;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.Title;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class Post extends AggregateEvent<PostId> {
    private Author author;
    private Title title;
    private Date postedAt;

    private Post(PostId entityId) {
        super(entityId);
        super.subscribe(new PostEventListener(this));
    }

    public Post(PostId entityId, Author author, Title title, Date postedAt) {
        super(entityId);
        this.author = author;
        this.title = title;
        this.postedAt = postedAt;
        super
                .appendChange(new CreatedPost(entityId, title, author, postedAt))
                .apply();
    }

    public static Post from(PostId postId, List<DomainEvent> events) {
        Post post = new Post(postId);
        events.forEach(post::applyEvent);
        return post;
    }

    public void addComment(PostId postId, CommentId commentId, Author commentAuthor, Content commentContent) {
        super
                .appendChange(new AddedComment(postId, commentId, commentAuthor, commentContent))
                .apply();
    }

    public Author author() {
        return author;
    }

    public Title title() {
        return title;
    }

    public Date postedAt() {
        return postedAt;
    }
}
