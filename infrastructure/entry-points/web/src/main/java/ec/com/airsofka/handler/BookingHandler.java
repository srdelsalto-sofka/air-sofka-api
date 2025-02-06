package ec.com.airsofka.handler;


import ec.com.airsofka.booking.commands.CreateBookingCommand;
import ec.com.airsofka.booking.commands.usecases.CreateBookingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BookingHandler {
    private final CreateBookingUseCase createBookingUseCase;

    public BookingHandler(CreateBookingUseCase createBookingUseCase) {
        this.createBookingUseCase = createBookingUseCase;
    }


    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateBookingCommand.class)
                .flatMap(createBookingUseCase::execute)
                .flatMap(flightResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(flightResponse)
                );
    }

}
