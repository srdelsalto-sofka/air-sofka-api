package ec.com.airsofka.config;

import ec.com.airsofka.rabbit.RabbitProperties;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    private final RabbitProperties envProperties;


    public RabbitConfig(RabbitProperties envProperties) {
        this.envProperties = envProperties;
    }

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(envProperties.getBookingExchange(), true, false);
    }


    @Bean
    public Queue bookingQueue() {
        return new Queue(envProperties.getBookingQueue(), true);
    }

    @Bean
    public Binding bookingBinding() {
        return BindingBuilder.bind(bookingQueue())
                .to(bookingExchange())
                .with(envProperties.getBookingRoutingKey());
    }


    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeBeans(AmqpAdmin amqpAdmin) {
        return event -> {


            amqpAdmin.declareExchange(bookingExchange());
            amqpAdmin.declareQueue(bookingQueue());
            amqpAdmin.declareBinding(bookingBinding());
        };
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplateBean(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
