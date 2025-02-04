package ec.com.airsofka.adapter;

import ec.com.airsofka.gateway.FlightRepository;
import ec.com.airsofka.gateway.dto.FlightDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FlightMongoAdapter implements FlightRepository {

    @Override
    public Mono<FlightDTO> save(FlightDTO flightDTO) {
        return null;
    }
}
