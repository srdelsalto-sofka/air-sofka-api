package ec.com.airsofka.aggregate.reservation.events;

import ec.com.airsofka.generics.domain.DomainEvent;

public class BookingCreated  extends DomainEvent {

    private String id;
    private String status;
    public BookingCreated(String id, String status) {
        super(EventsReservationEnum.BOOKING_CREATED.name());

        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
