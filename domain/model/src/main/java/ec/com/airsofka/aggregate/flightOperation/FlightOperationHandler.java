package ec.com.airsofka.aggregate.flightOperation;

import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.aggregate.flightOperation.events.SeatListCreated;
import ec.com.airsofka.aggregate.flightOperation.events.SeatReserved;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.flight.values.FlightId;
import ec.com.airsofka.flight.values.objects.*;
import ec.com.airsofka.flight.values.objects.Price;
import ec.com.airsofka.generics.domain.DomainActionsContainer;
import ec.com.airsofka.plane.values.PlaneId;
import ec.com.airsofka.seat.Seat;
import ec.com.airsofka.seat.values.SeatId;
import ec.com.airsofka.seat.values.objects.*;
import ec.com.airsofka.seat.values.objects.Number;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

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

        addDomainActions((SeatListCreated event) -> {
            List<Seat> seatList = event.getSeats().stream()
                    .map(seats -> new Seat(
                            new SeatId(), Number.of(seats.getNumber()), Row.of(seats.getRow()),
                            Column.of(seats.getColumn()), Type.of(seats.getType()), Status.of(seats.getStatus()),
                            ec.com.airsofka.seat.values.objects.Price.of(seats.getPrice()), FlightId.of(seats.getIdFlight())

                    ))
                    .toList();
            flightOperation.setSeatList(seatList);

        });

        addDomainActions((SeatReserved event) -> {
            List<Seat> updatedSeats = flightOperation.getSeatList().stream()
                    .map(seat -> seat.getId().getValue().equals(event.getSeatId())
                                    ? new Seat(
                                    SeatId.of(event.getSeatId()),
                                    Number.of(event.getNumber()),
                                    Row.of(event.getRow()),
                                    Column.of(event.getColumn()),
                                    Type.of(event.getType()),
                                    Status.of(event.getStatus()),
                                    ec.com.airsofka.seat.values.objects.Price.of(event.getPrice()),
                                    FlightId.of(event.getIdFlight())
                            )
                                    : seat
                    )
                    .toList();

            flightOperation.setSeatList(updatedSeats);
        });
    }
}

//en la parte de model, donde esta Seat tener un array de seatCreatedDTO con toda la distribucion de asientos en una clase SeatData (con tipo 15 asientos ya hardcodeados). Y en el caso de uso de createflight le pasas ese array y listo
//caso de uso de CreateFlight llamar eso
//trabajar mientras con 5 o 6 asientos (coleccion de Seats)
//caso de uso de query para traer los asientos. GetSeatsByFlightId. Y ahi ya tengo el status
// funciona como una mini reserva o pre reserva antes de enviar la reserva del vuelo completa. UpdateSeatStatusUseCase (le paso el id del asiento y el status).