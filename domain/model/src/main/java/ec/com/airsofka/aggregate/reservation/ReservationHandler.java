package ec.com.airsofka.aggregate.reservation;


import ec.com.airsofka.aggregate.reservation.events.BookingCreated;
import ec.com.airsofka.booking.Booking;
import ec.com.airsofka.booking.values.BookingId;
import ec.com.airsofka.booking.values.objects.Status;
import ec.com.airsofka.generics.domain.DomainActionsContainer;

public class ReservationHandler extends DomainActionsContainer {
    public ReservationHandler(Reservation reservation) {
        addDomainActions((BookingCreated event) -> {
            Booking booking = new Booking(
                    BookingId.of(event.getId()),
                    Status.of(event.getStatus())
            );

            reservation.setBooking(booking);
        });
    }
}
