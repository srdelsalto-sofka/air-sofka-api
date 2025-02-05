package ec.com.airsofka.database.events;

import ec.com.airsofka.data.EventEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface IEventMongoRepository extends ReactiveMongoRepository<EventEntity, String> {
    Flux<EventEntity> findAllByAggregateId(String aggregateId);
    Flux<EventEntity> findAllByAggregateRootName(String aggregateRootName);
    Flux<EventEntity> findAllByAggregateRootNameAndEventType(String aggregateRootName, String eventType);

}
