package co.com.post_comments.alpha.application.adapters.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class EventDocument {

    private String aggregateRootId;
    private String eventJSON;
    private LocalDateTime timestamp;
    private String eventType;

    public String aggregateRootId() {
        return aggregateRootId;
    }

    public String eventJSON() {
        return eventJSON;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public String eventType() {
        return eventType;
    }
}
