package ec.com.airsofka.gateway;

import ec.com.airsofka.generics.domain.DomainEvent;
import reactor.core.publisher.Mono;

public interface BusEvent {
    void sendEventFlightCreated(Mono<DomainEvent> event);
    void sendEventBookingCreated(Mono<DomainEvent> event);
    void sendEventUserCreated(Mono<DomainEvent> event);

}
