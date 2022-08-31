package co.com.post_comments.alpha.application.adapters.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class EventDocument {

    private String aggregateRootId;
    private String json;
    private LocalDateTime timestamp;
    private String eventType;

    public String aggregateRootId() {
        return aggregateRootId;
    }

    public String json() {
        return json;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public String eventType() {
        return eventType;
    }
}
