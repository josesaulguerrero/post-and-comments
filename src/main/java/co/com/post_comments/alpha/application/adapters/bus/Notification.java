package co.com.post_comments.alpha.application.adapters.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;


public class Notification {
    private final String type;
    private final String body;
    private final Instant instant;

    @Autowired
    private ObjectMapper jsonMapper;

    public Notification(String type, String body) {
        this.type = type;
        this.body = body;
        this.instant = Instant.now();
    }
    private Notification(){
        this(null,null);
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
        try {
            return this.jsonMapper.readValue(stringifiedNotification, Notification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't deserialize the given String");
        }
    }

    public String serialize() {
        try {
            return this.jsonMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't serialize the Notification");
        }
    }

    public static Notification from(String aNotification){
        return new Notification().deserialize(aNotification);
    }
}
