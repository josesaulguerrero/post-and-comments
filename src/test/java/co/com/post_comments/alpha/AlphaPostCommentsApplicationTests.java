package co.com.post_comments.alpha;

import co.com.post_comments.alpha.domain.post.events.CommentAdded;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.identities.CommentId;
import co.com.post_comments.alpha.domain.post.values.identities.PostId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@Slf4j
class AlphaPostCommentsApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper()
				.registerModule(new JavaTimeModule());
		CommentAdded event = new CommentAdded(
				new PostId(),
				new CommentId(),
				new Author("Pepito"),
				new Content("a comment"),
				new Date(LocalDateTime.now())
		);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
		log.debug(json);
	}

}
