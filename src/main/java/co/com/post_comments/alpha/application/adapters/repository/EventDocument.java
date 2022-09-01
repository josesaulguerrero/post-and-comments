package co.com.post_comments.alpha.application.adapters.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class EventDocument {

    @Id
    private String oid;
    private String aggregateRootId;
    private String eventJSON;
    private LocalDateTime timestamp;
    private String eventType;

    public String oid() {
        return oid;
    }

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
