package ec.com.airsofka.handler;

import ec.com.airsofka.flight.commands.CreateFlightCommand;
import ec.com.airsofka.flight.commands.usecases.CreateFlightUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlightHandler {
    private final CreateFlightUseCase createFlightUseCase;

    public FlightHandler(CreateFlightUseCase createFlightUseCase) {
        this.createFlightUseCase = createFlightUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateFlightCommand.class)
                .flatMap(createFlightUseCase::execute)
                .flatMap(flightResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(flightResponse)
                );
    }
}
