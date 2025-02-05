package ec.com.airsofka.aggregate.reservation;

import ec.com.airsofka.aggregate.flightOperation.FlightOperation;
import ec.com.airsofka.aggregate.flightOperation.FlightOperationHandler;
import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.aggregate.flightOperation.values.FlightOperationId;
import ec.com.airsofka.aggregate.reservation.events.BillingCreated;
import ec.com.airsofka.aggregate.reservation.events.BookingCreated;
import ec.com.airsofka.aggregate.reservation.values.ReservationId;
import ec.com.airsofka.booking.Booking;
import ec.com.airsofka.booking.values.BookingId;
import ec.com.airsofka.booking.values.objects.Status;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.flight.values.FlightId;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.utils.AggregateRoot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class Reservation  extends AggregateRoot<ReservationId> {



    private Booking booking;

    public Reservation() {
        super(new ReservationId());
        setSubscription(new ReservationHandler(this));
    }

    public Reservation(final String id) {
        super(ReservationId.of(id));
        setSubscription(new ReservationHandler(this));
    }



    public void createReservation(String status) {
        addEvent(new BookingCreated(new BookingId().getValue(), status)).apply();
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public static Mono<Reservation> from(final String id, Flux<DomainEvent> events) {
        Reservation reservation = new Reservation(id);

        return events
                .filter(eventsFilter -> id.equals(eventsFilter.getAggregateRootId()))
                .flatMap(event -> Mono.fromRunnable(() -> reservation.addEvent(event).apply()))
                .doOnTerminate(reservation::markEventsAsCommitted)
                .then(Mono.just(reservation));
    }
}
