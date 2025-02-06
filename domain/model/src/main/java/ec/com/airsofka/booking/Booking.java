package ec.com.airsofka.booking;

import ec.com.airsofka.booking.values.BookingId;
import ec.com.airsofka.booking.values.objects.Status;
import ec.com.airsofka.generics.utils.Entity;

public class Booking extends Entity<BookingId> {
    private final Status status;


    public Booking(BookingId id, Status status) {
        super(id);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
