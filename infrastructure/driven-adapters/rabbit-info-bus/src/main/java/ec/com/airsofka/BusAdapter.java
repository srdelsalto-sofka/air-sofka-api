package ec.com.airsofka;

import ec.com.airsofka.config.RabbitProperties;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.generics.domain.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BusAdapter implements BusEvent {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties envProperties;

    public BusAdapter(RabbitTemplate rabbitTemplate, RabbitProperties envProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.envProperties = envProperties;
    }

    @Override
    public void sendEventFlightCreated(Mono<DomainEvent> event) {
        event.subscribe(domainEvent -> {
            rabbitTemplate.convertAndSend(envProperties.getFlightCreatedExchange(), envProperties.getFlightCreatedRoutingKey(), domainEvent);
        });
    }

    @Override
    public void sendEventBookingCreated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getBookingExchange(), envProperties.getBookingRoutingKey(),
                        domainEvent)
        );

    }

    public void sendEventUserCreated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getUserCreatedExchange(), envProperties.getUserCreatedRoutingKey(),
                        domainEvent
                )
        );
    }

    @Override
    public void sendEventUserUpdated(Mono<DomainEvent> event) {
        event.subscribe(domainEvent -> {
            rabbitTemplate.convertAndSend(envProperties.getUserUpdatedExchange(), envProperties.getUserUpdatedRoutingKey(), domainEvent);
        });
    }

    public void sendEventPlaneCreated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getPlaneCreatedExchange(), envProperties.getPlaneCreatedRoutingKey(),
                        domainEvent
                )
        );
    }

    @Override
    public void sendEventSeatReserved(Mono<DomainEvent> event) {
        event.subscribe(domainEvent -> {
            rabbitTemplate.convertAndSend(envProperties.getSeatReservedExchange(), envProperties.getSeatReservedRoutingKey(), domainEvent);
        });
    }

    @Override
    public void sendEventMaintenanceCreated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getMaintenanceExchange(), envProperties.getMaintenanceRoutingKey(),
                        domainEvent
                )
        );
    }

    @Override
    public void sendEventPlaneUpdated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getPlaneUpdatedExchange(), envProperties.getPlaneUpdatedRoutingKey(),
                        domainEvent
                )
        );
    }

    @Override
    public void sendEventSeatListCreated(Mono<DomainEvent> event) {
        event.subscribe(
                domainEvent -> rabbitTemplate.convertAndSend(
                        envProperties.getSeatCreatedExchange(), envProperties.getSeatCreatedRoutingKey(),
                        domainEvent
                )
        );
    }

}
