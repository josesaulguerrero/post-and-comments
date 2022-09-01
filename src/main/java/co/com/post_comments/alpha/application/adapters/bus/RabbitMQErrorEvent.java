package co.com.post_comments.alpha.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;

public class RabbitMQErrorEvent extends DomainEvent {
    private final String classType;
    private final String message;

    public RabbitMQErrorEvent(String classType, String message){
        super("post.error");
        this.classType = classType;
        this.message = message;
    }

    public String getClassType() {
        return classType;
    }

    public String getMessage() {
        return message;
    }
}
