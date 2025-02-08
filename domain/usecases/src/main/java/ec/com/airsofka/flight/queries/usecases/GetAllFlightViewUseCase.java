package ec.com.airsofka.flight.queries.usecases;

import ec.com.airsofka.flight.queries.query.GetAllFlightQuery;
import ec.com.airsofka.flight.queries.responses.FlightResponse;
import ec.com.airsofka.gateway.FlightRepository;
import ec.com.airsofka.generics.interfaces.IUseCaseGet;
import ec.com.airsofka.generics.utils.QueryResponse;
import reactor.core.publisher.Mono;

public class GetAllFlightViewUseCase implements IUseCaseGet<GetAllFlightQuery, FlightResponse> {
    private final FlightRepository flightRepository;

    public GetAllFlightViewUseCase(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Mono<QueryResponse<FlightResponse>> get(GetAllFlightQuery filters) {
        return flightRepository.getAll(filters)
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
