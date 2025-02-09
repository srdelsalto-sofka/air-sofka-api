package ec.com.airsofka.flight.queries.usecases;

import ec.com.airsofka.flight.queries.responses.FlightResponse;
import ec.com.airsofka.gateway.IFlightRepository;
import ec.com.airsofka.generics.interfaces.IUseCaseEmptyGet;
import ec.com.airsofka.generics.utils.QueryResponse;
import reactor.core.publisher.Mono;

public class GetAllFlightViewUseCase implements IUseCaseEmptyGet<FlightResponse> {
    private final IFlightRepository flightRepository;

    public GetAllFlightViewUseCase(IFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Mono<QueryResponse<FlightResponse>> get() {
        return flightRepository.getAll()
                .map(flightDTO ->
                        new FlightResponse(
                                flightDTO.getId(),
                                flightDTO.getOrigin(),
                                flightDTO.getDestination(),
                                flightDTO.getDeparture(),
                                flightDTO.getArrival(),
                                flightDTO.getPrice(),
                                flightDTO.getIdPlane()
                        ))
                .collectList()
                .flatMap(flights -> Mono.just(QueryResponse.ofMultiple(flights)));
    }
}
