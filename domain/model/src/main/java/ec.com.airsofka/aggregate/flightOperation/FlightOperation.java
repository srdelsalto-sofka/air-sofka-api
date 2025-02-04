package ec.com.airsofka.aggregate.flightOperation;

import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.aggregate.flightOperation.values.FlightOperationId;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.flight.values.FlightId;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.utils.AggregateRoot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class FlightOperation extends AggregateRoot<FlightOperationId> {
    private Flight flight;

    public FlightOperation() {
        super(new FlightOperationId());
        setSubscription(new FlightOperationHandler(this));
    }

    public FlightOperation(final String id) {
        super(FlightOperationId.of(id));
        setSubscription(new FlightOperationHandler(this));
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void createFlight(String origin, String destination, LocalDateTime departure, LocalDateTime arrival, Double price, String idPlane) {
        addEvent(new FlightCreated(new FlightId().getValue(), origin, destination, departure, arrival, price, idPlane)).apply();
    }

    public static Mono<FlightOperation> from(final String id, Flux<DomainEvent> events) {
        FlightOperation flightOperation = new FlightOperation(id);

        return events
                .filter(eventsFilter -> id.equals(eventsFilter.getAggregateRootId()))
                .flatMap(event -> Mono.fromRunnable(() -> flightOperation.addEvent(event).apply()))
                .doOnTerminate(flightOperation::markEventsAsCommitted)
                .then(Mono.just(flightOperation));
    }
}
