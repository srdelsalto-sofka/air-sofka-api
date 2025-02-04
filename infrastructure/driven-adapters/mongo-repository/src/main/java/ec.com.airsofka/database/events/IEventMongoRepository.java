package ec.com.airsofka.database.events;

import ec.com.airsofka.data.EventEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IEventMongoRepository extends ReactiveMongoRepository<EventEntity, String> {
}
