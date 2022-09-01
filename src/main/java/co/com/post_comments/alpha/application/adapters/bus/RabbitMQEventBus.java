package co.com.post_comments.alpha.application.adapters.bus;

import co.com.post_comments.alpha.application.config.RabbitMQConfig;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.sofka.domain.generic.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventBus implements EventBus {
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper jsonMapper;

    public RabbitMQEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishDomainEvent(DomainEvent event) {
        try {
            Notification notification = new Notification(
                    event.getClass().getTypeName(),
                    this.jsonMapper.writeValueAsString(event)
            );
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.MAIN_ROUTING_KEY,
                    notification.serialize().getBytes()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishError(Throwable errorEvent) {
        try {
            ErrorEvent event = new ErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
            Notification notification = new Notification(
                    event.getClass().getTypeName(),
                    this.jsonMapper.writeValueAsString(event)
            );
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.MAIN_ROUTING_KEY,
                    notification.serialize().getBytes()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
