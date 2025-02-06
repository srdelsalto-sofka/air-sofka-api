package ec.com.airsofka.booking.commands.usecases;


import ec.com.airsofka.aggregate.reservation.Reservation;
import ec.com.airsofka.booking.commands.CreateBookingCommand;
import ec.com.airsofka.booking.queries.responses.BookingResponse;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.user.queries.query.GetByElementQuery;
import ec.com.airsofka.user.queries.usecases.FrequentUserUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateBookingUseCase  implements IUseCaseExecute<CreateBookingCommand, BookingResponse> {
    private final IEventStore repository;
    private final BusEvent busEvent;

    public CreateBookingUseCase(IEventStore repository, BusEvent busEvent) {
        this.repository = repository;
        this.busEvent = busEvent;
    }

    @Override
    public Mono<BookingResponse> execute(CreateBookingCommand cmd) {

        Reservation reservation = new Reservation();
        reservation.createReservation(cmd.getStatus());

        reservation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventBookingCreated);

        reservation.markEventsAsCommitted();
        return Mono.just(new BookingResponse("TESTED"));
    }
}
