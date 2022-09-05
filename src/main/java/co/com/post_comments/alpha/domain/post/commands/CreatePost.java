package co.com.post_comments.alpha.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.*;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreatePost extends Command {
    private String author;
    private String title;
    private String content;
    private String postedAt;

    public String author() {
        return author;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public String postedAt() {
        return postedAt;
    }
}
