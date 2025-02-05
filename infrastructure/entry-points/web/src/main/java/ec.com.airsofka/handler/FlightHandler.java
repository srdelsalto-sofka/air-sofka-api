package ec.com.airsofka.handler;

import ec.com.airsofka.flight.commands.CreateFlightCommand;
import ec.com.airsofka.flight.commands.usecases.CreateFlightUseCase;
import ec.com.airsofka.flight.queries.usecases.GetAllFlightViewUseCase;
import ec.com.airsofka.generics.utils.QueryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlightHandler {
    private final CreateFlightUseCase createFlightUseCase;
    private final GetAllFlightViewUseCase getAllFlightViewUseCase;

    public FlightHandler(CreateFlightUseCase createFlightUseCase, GetAllFlightViewUseCase getAllFlightViewUseCase) {
        this.createFlightUseCase = createFlightUseCase;
        this.getAllFlightViewUseCase = getAllFlightViewUseCase;
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

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return getAllFlightViewUseCase.get()
                .map(QueryResponse::getMultipleResults)
                .flatMap(flightResponses ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(flightResponses)
                );
    }
}
