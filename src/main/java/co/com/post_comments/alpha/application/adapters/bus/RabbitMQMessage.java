package co.com.post_comments.alpha.application.adapters.bus;

import co.com.post_comments.alpha.application.commons.json.JSONMapper;
import co.com.post_comments.alpha.application.commons.json.JSONMapperImpl;
import lombok.Getter;

import java.time.Instant;

@Getter

public class RabbitMQMessage {
    private static final JSONMapper jsonMapper = new JSONMapperImpl();
    private final String type;
    private final String body;
    private final Instant instant;


    public RabbitMQMessage(String type, String body) {
        this.type = type;
        this.body = body;
        this.instant = Instant.now();
    }

    public String serialize() {
        return jsonMapper.writeToJson(this);
    }

    public static RabbitMQMessage from(String stringifiedNotification) {
        return RabbitMQMessage.deserialize(stringifiedNotification);
    }

    public static RabbitMQMessage deserialize(String stringifiedNotification) {
        return (RabbitMQMessage) jsonMapper.readFromJson(stringifiedNotification, RabbitMQMessage.class);
    }
}
