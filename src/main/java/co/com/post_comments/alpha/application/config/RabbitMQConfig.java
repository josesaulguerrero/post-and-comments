package co.com.post_comments.alpha.application.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "main.exchange";

    public static final String MAIN_QUEUE = "events.main";

    public static final String MAIN_ROUTING_KEY = "routingKey.main";

    @Bean
    public Queue generalQueue(){
        return new Queue(MAIN_QUEUE);
    }

    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding BindingToGeneralQueue() {
        return BindingBuilder
                .bind(generalQueue())
                .to(getTopicExchange())
                .with(MAIN_ROUTING_KEY);
    }
}
