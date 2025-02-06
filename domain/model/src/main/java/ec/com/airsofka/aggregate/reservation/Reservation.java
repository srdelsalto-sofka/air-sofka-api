package ec.com.airsofka.aggregate.reservation;


import ec.com.airsofka.aggregate.reservation.events.BookingCreated;
import ec.com.airsofka.aggregate.reservation.values.ReservationId;
import ec.com.airsofka.billing.Billing;
import ec.com.airsofka.booking.Booking;
import ec.com.airsofka.booking.values.BookingId;
import ec.com.airsofka.contact.Contact;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.utils.AggregateRoot;
import ec.com.airsofka.passenger.Passenger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Reservation  extends AggregateRoot<ReservationId> {



    private Booking booking;
    private Contact contact;
    private List<Passenger> passengers;
    private Billing billing;

    public Reservation() {
        super(new ReservationId());
        setSubscription(new ReservationHandler(this));
    }

    public Reservation(final String id) {
        super(ReservationId.of(id));
        setSubscription(new ReservationHandler(this));
    }



    public void createReservation(String status) {

    }

    public void createBooking() {

    }

    public void createBilling() {

    }

    public void createContact() {
    }

    public void createPassengers() {
    }




    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
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
