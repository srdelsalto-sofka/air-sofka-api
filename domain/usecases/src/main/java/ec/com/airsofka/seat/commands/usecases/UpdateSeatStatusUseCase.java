package ec.com.airsofka.seat.commands.usecases;

import ec.com.airsofka.aggregate.flightOperation.FlightOperation;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.seat.Seat;
import ec.com.airsofka.seat.commands.UpdateSeatStatusCommand;
import ec.com.airsofka.seat.queries.responses.SeatResponse;
import ec.com.airsofka.seat.values.objects.Status;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
        // 1️⃣ Recuperar el FlightOperation desde el repositorio con su historial de eventos
        return repository.getEventsByAggregateId(cmd.getIdFlight())
                .collectList()
                .flatMap(events -> FlightOperation.from(cmd.getIdFlight(), Flux.fromIterable(events)))
                .flatMap(flightOperation -> {
                    // 2️⃣ Buscar el asiento a actualizar
                    List<Seat> seats = flightOperation.getSeatList();
                    Seat updatedSeat = seats.stream()
                            .filter(seat -> seat.getId().getValue().equals(cmd.getSeatId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Seat not found: " + cmd.getSeatId()));

                    // 3️⃣ Crear una nueva lista con el asiento actualizado
                    List<Seat> updatedSeats = seats.stream()
                            .map(seat -> seat.getId().getValue().equals(cmd.getSeatId())
                                    ? new Seat(seat.getId(), seat.getNumber(), seat.getRow(), seat.getColumn(),
                                    seat.getType(), new Status(cmd.getStatus()), seat.getPrice(), seat.getIdFlight())
                                    : seat)
                            .toList();

                    flightOperation.setSeatList(updatedSeats);

                    // 4️⃣ Disparar el evento de SeatReserved para notificar el cambio
                    flightOperation.createSeatReservation(
                            updatedSeat.getNumber().getValue(),
                            updatedSeat.getRow().getValue(),
                            updatedSeat.getColumn().getValue(),
                            updatedSeat.getType().getValue(),
                            cmd.getStatus(),
                            updatedSeat.getPrice().getValue(),
                            updatedSeat.getIdFlight().getValue()
                    );

                    // 5️⃣ Guardar eventos y notificar al bus de eventos
                    return Flux.fromIterable(flightOperation.getUncommittedEvents())
                            .flatMap(repository::save) // Guardar cada evento en el EventStore
                            .doOnNext(event -> busEvent.sendEventSeatReserved(Mono.just(event))) // Enviar eventos al BusEvent
                            .then(Mono.fromRunnable(flightOperation::markEventsAsCommitted)) // Marcar eventos como comprometidos
                            .thenReturn(updatedSeat); // Propagar el asiento actualizado
                })
                // 6️⃣ Convertir el asiento actualizado en la respuesta esperada
                .map(updatedSeat -> new SeatResponse(
                        updatedSeat.getId().getValue(),
                        updatedSeat.getNumber().getValue(),
                        updatedSeat.getRow().getValue(),
                        updatedSeat.getColumn().getValue(),
                        updatedSeat.getType().getValue(),
                        updatedSeat.getStatus().getValue(),
                        updatedSeat.getPrice().getValue(),
                        updatedSeat.getIdFlight().getValue()
                ));
    }
}

