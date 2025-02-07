package ec.com.airsofka.gateway;

import ec.com.airsofka.generics.domain.DomainEvent;
import reactor.core.publisher.Mono;

public interface BusEvent {
    void sendEventFlightCreated(Mono<DomainEvent> event);

    void sendEventBookingCreated(Mono<DomainEvent> event);

    void sendEventUserCreated(Mono<DomainEvent> event);

    void sendEventUserUpdated(Mono<DomainEvent> event);

    void sendEventPlaneCreated(Mono<DomainEvent> event);

    void sendEventSeatReserved(Mono<DomainEvent> event);
    void sendEventMaintenanceCreated(Mono<DomainEvent> event);

    void sendEventPlaneUpdated(Mono<DomainEvent> event);

    void sendEventSeatListCreated(Mono<DomainEvent> event);
    void sendEmailNotification(Mono<DomainEvent> event);
    void sendEventBillingCreated(Mono<DomainEvent> event);
    void sendEventContactCreated(Mono<DomainEvent> event);
    void sendEventPassengerCreated(Mono<DomainEvent> event);

}
