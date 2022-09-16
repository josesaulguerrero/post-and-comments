package co.com.post_comments.alpha.application.adapters.bus;

import co.com.post_comments.alpha.application.commons.json.JSONMapper;
import co.com.post_comments.alpha.application.commons.json.JSONMapperImpl;
import co.com.post_comments.alpha.application.config.RabbitMQConfig;
import co.com.post_comments.alpha.business.gateways.EventBus;
import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RabbitMQEventBus implements EventBus {
    private static final JSONMapper jsonMapper = new JSONMapperImpl();
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishDomainEvent(DomainEvent event) {
        log.info("Publishing a domain event to RabbitMQ with the following information: " + event);
        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage(
                event.getClass().getTypeName(),
                jsonMapper.writeToJson(event)
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.MAIN_ROUTING_KEY,
                rabbitMQMessage.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        log.error("Publishing an error to RabbitMQ with the following information: " + errorEvent);
        RabbitMQErrorEvent event = new RabbitMQErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage(
                event.getClass().getTypeName(),
                jsonMapper.writeToJson(event)
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.MAIN_ROUTING_KEY,
                rabbitMQMessage.serialize().getBytes()
        );
    }
}
