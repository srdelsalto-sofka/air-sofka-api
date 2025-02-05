package ec.com.airsofka.seat.commands.usecases;

import ec.com.airsofka.aggregate.flightOperation.FlightOperation;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.seat.Seat;
import ec.com.airsofka.seat.commands.UpdateSeatStatusCommand;
import ec.com.airsofka.seat.queries.responses.SeatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateSeatStatusUseCase implements IUseCaseExecute<UpdateSeatStatusCommand, SeatResponse> {
    private final IEventStore repository;
    private final BusEvent busEvent;

    public UpdateSeatStatusUseCase(IEventStore repository, BusEvent busEvent) {
        this.repository = repository;
        this.busEvent = busEvent;
    }

    @Override
    public Mono<SeatResponse> execute(UpdateSeatStatusCommand cmd) {
        FlightOperation flightOperation = new FlightOperation();

        flightOperation.createSeatReservation(cmd.getSeatId, cmd.getStatus());

        flightOperation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventSeatReserved);

        flightOperation.markEventsAsCommitted();

        Seat newSeat = flightOperation.getSeatList();
        return Mono.just(new SeatResponse(
                newSeat.getId().getValue(),
                        newSeat.getNumber().getValue(),
                )
        );
    }
}
