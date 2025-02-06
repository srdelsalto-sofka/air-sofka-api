package ec.com.airsofka.aggregate.flightOperation;

import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.flight.values.FlightId;
import ec.com.airsofka.flight.values.objects.*;
import ec.com.airsofka.generics.domain.DomainActionsContainer;
import ec.com.airsofka.plane.values.PlaneId;

public class FlightOperationHandler extends DomainActionsContainer {
    public FlightOperationHandler(FlightOperation flightOperation) {
        addDomainActions((FlightCreated event) -> {
            Flight flight = new Flight(
                    FlightId.of(event.getId()),
                    Origin.of(event.getOrigin()),
                    Destination.of(event.getDestination()),
                    Departure.of(event.getDeparture()),
                    Arrival.of(event.getArrival()),
                    Price.of(event.getPrice()),
                    PlaneId.of(event.getIdPlane())
            );

            flightOperation.setFlight(flight);
        });
    }
}
