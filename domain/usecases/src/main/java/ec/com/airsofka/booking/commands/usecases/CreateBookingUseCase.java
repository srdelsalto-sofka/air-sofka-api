package ec.com.airsofka.booking.commands.usecases;


import ec.com.airsofka.aggregate.reservation.Reservation;
import ec.com.airsofka.booking.commands.CreateBookingCommand;
import ec.com.airsofka.booking.queries.responses.BookingResponse;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.passenger.PassengerCreatedDTO;
import ec.com.airsofka.user.queries.query.GetByElementQuery;
import ec.com.airsofka.user.queries.usecases.FrequentUserUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateBookingUseCase implements IUseCaseExecute<CreateBookingCommand, BookingResponse> {
    private final IEventStore repository;
    private final BusEvent busEvent;

    public CreateBookingUseCase(IEventStore repository, BusEvent busEvent) {
        this.repository = repository;
        this.busEvent = busEvent;
    }

    @Override
    public Mono<BookingResponse> execute(CreateBookingCommand cmd) {
        Reservation reservation = new Reservation();

        List<PassengerCreatedDTO> passengers =  cmd.getPassengers().stream()
                .map(command -> new PassengerCreatedDTO(
                        command.getPassengerTitle(),
                        command.getPassengerName(),
                        command.getPassengerLastName(),
                        command.getPassengerType(),
                        command.getSeatId()
                )).toList();

        reservation.createBooking(cmd.getBookingStatus(),
                cmd.getBookingPrice(),
                cmd.getDiscount(),
                cmd.getFlightId(),
                cmd.getUserId());

        reservation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventBookingCreated);
        reservation.markEventsAsCommitted();

        String bookingId = reservation.getBooking().getId().getValue();

        reservation.createBilling(bookingId, cmd.getPaymentMethod(), cmd.getBookingPrice());
        reservation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventBillingCreated);
        reservation.markEventsAsCommitted();

        reservation.createContact(bookingId, cmd.getEmail(), cmd.getPrefix(), cmd.getPhoneNumber());
        reservation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventContactCreated);
        reservation.markEventsAsCommitted();

        reservation.createPassengers(bookingId, passengers);
        reservation.getUncommittedEvents()
                .stream()
                .map(repository::save)
                .forEach(busEvent::sendEventPassengerCreated);

        reservation.markEventsAsCommitted();
        return Mono.just(new BookingResponse(reservation.getId().getValue()));
    }
}
