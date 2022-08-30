package co.com.post_comments.alpha;

import co.com.post_comments.alpha.domain.post.events.CommentAdded;
import co.com.post_comments.alpha.domain.post.values.Author;
import co.com.post_comments.alpha.domain.post.values.Content;
import co.com.post_comments.alpha.domain.post.values.Date;
import co.com.post_comments.alpha.domain.post.values.identities.CommentId;
import co.com.post_comments.alpha.domain.post.values.identities.PostId;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@Slf4j
class AlphaPostCommentsApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException, ClassNotFoundException {
		ObjectMapper mapper = new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		CommentAdded event = new CommentAdded(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				"Pepito",
				"A comment",
				LocalDateTime.now().toString()
		);

		String json = mapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(event);
		CommentAdded deserializedEvent = (CommentAdded) mapper.readValue(json, Class.forName(CommentAdded.class.getCanonicalName()));
		log.debug(deserializedEvent.toString());
		log.debug("hello " + event.getClass().toString() + " bye");
	}

}
