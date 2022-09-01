package co.com.post_comments.alpha.application.adapters.bus;

import co.com.post_comments.alpha.application.commons.JSONMapper;
import co.com.post_comments.alpha.application.commons.JSONMapperImpl;
import co.com.post_comments.alpha.application.config.RabbitMQConfig;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.sofka.domain.generic.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventBus implements EventBus {
    private static final JSONMapper jsonMapper = new JSONMapperImpl();
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishDomainEvent(DomainEvent event) {
        Notification notification = new Notification(
                event.getClass().getTypeName(),
                jsonMapper.writeToJson(event)
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.MAIN_ROUTING_KEY,
                notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        ErrorEvent event = new ErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
        Notification notification = new Notification(
                event.getClass().getTypeName(),
                jsonMapper.writeToJson(event)
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.MAIN_ROUTING_KEY,
                notification.serialize().getBytes()
        );
    }
}
