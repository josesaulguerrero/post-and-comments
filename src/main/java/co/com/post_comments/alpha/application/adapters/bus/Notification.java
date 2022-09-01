package co.com.post_comments.alpha.application.adapters.bus;

import co.com.post_comments.alpha.application.commons.JSONMapper;
import co.com.post_comments.alpha.application.commons.JSONMapperImpl;

import java.time.Instant;


public class Notification {
    private static final JSONMapper jsonMapper = new JSONMapperImpl();
    private final String type;
    private final String body;
    private final Instant instant;


    public Notification(String type, String body) {
        this.type = type;
        this.body = body;
        this.instant = Instant.now();
    }

    private Notification() {
        this(null, null);
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public Instant getInstant() {
        return instant;
    }

    public Notification deserialize(String stringifiedNotification) {
        return (Notification) jsonMapper.readFromJson(stringifiedNotification, Notification.class);
    }

    public String serialize() {
        return jsonMapper.writeToJson(this);
    }

    public static Notification from(String stringifiedNotification) {
        return new Notification().deserialize(stringifiedNotification);
    }
}
