package co.com.post_comments.alpha.domain.post.commands;

import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.Title;
import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreatePost extends Command {
    private final Title title;
    private final Author author;
    private final Date postedAt;

    public Title title() {
        return title;
    }

    public Author author() {
        return author;
    }

    public Date postedAt() {
        return postedAt;
    }
}
