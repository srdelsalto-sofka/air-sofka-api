package ec.com.airsofka.flight.queries.usecases;

import ec.com.airsofka.flight.queries.responses.FlightResponse;
import ec.com.airsofka.gateway.FlightRepository;
import ec.com.airsofka.generics.interfaces.IUseCaseEmptyGet;
import ec.com.airsofka.generics.utils.QueryResponse;
import ec.com.airsofka.seat.queries.usecases.GetPriceBySeatIdViewUseCase;
import ec.com.airsofka.seat.queries.usecases.GetSeatsByFlightIdViewUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class GetAllFlightViewUseCase implements IUseCaseEmptyGet<FlightResponse> {
    private final FlightRepository flightRepository;

    public GetAllFlightViewUseCase(FlightRepository flightRepository) {
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
