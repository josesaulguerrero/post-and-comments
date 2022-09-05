package co.com.post_comments.alpha.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCreated extends DomainEvent {
    private String postId;
    private String title;
    private String content;
    private String author;
    private String postedAt;

    public PostCreated() {
        super(PostCreated.class.getName());
    }

    public PostCreated(String postId, String title, String content, String author, String postedAt) {
        super(PostCreated.class.getName());
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.postedAt = postedAt;
    }

    public String postId() {
        return postId;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public String author() {
        return author;
    }

    public String postedAt() {
        return postedAt;
    }
}
