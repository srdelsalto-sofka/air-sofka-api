package ec.com.airsofka.gateway;


import ec.com.airsofka.generics.domain.DomainEvent;

public interface BusEventListener {

    void receiveBookingCreated(DomainEvent emailDetails);
    void receiveFlightCreated(DomainEvent flightCreated);
    void receivePlaneCreated(DomainEvent planeCreated);
    void receiveUserCreated(DomainEvent userCreated);
    void receiveUserUpdated(DomainEvent userUpdated);

    void receiveMailEvent(DomainEvent mailEvent);

}
