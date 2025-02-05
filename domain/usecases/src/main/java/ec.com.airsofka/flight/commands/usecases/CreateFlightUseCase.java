package ec.com.airsofka.flight.commands.usecases;

import ec.com.airsofka.aggregate.flightOperation.FlightOperation;
import ec.com.airsofka.seat.SeatCreatedDTO;
import ec.com.airsofka.flight.Flight;
import ec.com.airsofka.flight.commands.CreateFlightCommand;
import ec.com.airsofka.flight.queries.responses.FlightResponse;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.seat.SeatClass;
import ec.com.airsofka.seat.SeatStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreateFlightUseCase implements IUseCaseExecute<CreateFlightCommand, FlightResponse> {
    private final IEventStore repository;
    private final BusEvent busEvent;

    public CreateFlightUseCase(IEventStore repository, BusEvent busEvent) {
        this.repository = repository;
        this.busEvent = busEvent;
    }

    @Override
    public Mono<FlightResponse> execute(CreateFlightCommand cmd) {
        FlightOperation flightOperation = new FlightOperation();

        flightOperation.createFlight(cmd.getOrigin(), cmd.getDestination(), cmd.getDeparture(), cmd.getArrival(), cmd.getPrice(), cmd.getIdPlane());

        /*flightOperation.createSeatList(

                 List.of(
                        new SeatCreatedDTO("A1", 1, "A", SeatClass.ECONOMY, SeatStatus.AVAILABLE, new BigDecimal("150.00"), "FL123"),
                        new SeatCreatedDTO( "A2", 1, "B", SeatClass.ECONOMY, SeatStatus.OCCUPIED, new BigDecimal("150.00"), "FL123"),
                        new SeatCreatedDTO( "A3", 1, "C", SeatClass.ECONOMY, SeatStatus.AVAILABLE, new BigDecimal("150.00"), "FL123"),
                        new SeatCreatedDTO( "B1", 2, "A", SeatClass.BUSINESS, SeatStatus.AVAILABLE, new BigDecimal("300.00"), "FL456"),
                        new SeatCreatedDTO( "B2", 2, "B", SeatClass.BUSINESS, SeatStatus.OCCUPIED, new BigDecimal("300.00"), "FL456"),
                        new SeatCreatedDTO( "C1", 3, "A", SeatClass.FIRST, SeatStatus.AVAILABLE, new BigDecimal("500.00"), "FL789"),
                        new SeatCreatedDTO( "C2", 3, "B", SeatClass.FIRST, SeatStatus.OCCUPIED, new BigDecimal("500.00"), "FL789")
                )
        );

        System.out.println(flightOperation.getSeatList().get(1).getIdFlight());*/

        flightOperation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventFlightCreated);

        flightOperation.markEventsAsCommitted();

        Flight newFlight = flightOperation.getFlight();
        return Mono.just(new FlightResponse(
                        newFlight.getId().getValue(),
                        newFlight.getOrigin().getValue(),
                        newFlight.getDestination().getValue(),
                        newFlight.getDeparture().getValue(),
                        newFlight.getArrival().getValue(),
                        newFlight.getPrice().getValue(),
                        newFlight.getIdPlane().getValue()
                )
        );
    }
}
