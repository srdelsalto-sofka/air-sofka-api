package ec.com.airsofka.adapter;

import ec.com.airsofka.data.FlightEntity;
import ec.com.airsofka.database.airsofka.FlightMongoRepository;
import ec.com.airsofka.gateway.IFlightRepository;
import ec.com.airsofka.gateway.dto.FlightDTO;
import ec.com.airsofka.mapper.FlightMapperEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FlightMongoAdapter implements IFlightRepository {

    private final FlightMongoRepository repository;
    private final ReactiveMongoTemplate airMongoTemplate;

    public FlightMongoAdapter(FlightMongoRepository repository, @Qualifier("airMongoTemplate") ReactiveMongoTemplate airMongoTemplate) {
        this.repository = repository;
        this.airMongoTemplate = airMongoTemplate;
    }

    @Override
    public Mono<FlightDTO> save(FlightDTO flightDTO) {
        FlightEntity flightEntity = FlightMapperEntity.toEntity(flightDTO);
        return repository.save(flightEntity).map(FlightMapperEntity::fromEntity);
    }

    @Override
    public Flux<FlightDTO> getAll() {
        return repository.findAll().map(FlightMapperEntity::fromEntity);
    }
}
