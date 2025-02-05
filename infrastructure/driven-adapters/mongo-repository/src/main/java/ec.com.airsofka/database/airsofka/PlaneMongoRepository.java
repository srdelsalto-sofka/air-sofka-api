package ec.com.airsofka.database.airsofka;

import ec.com.airsofka.data.PlaneEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlaneMongoRepository extends ReactiveMongoRepository<PlaneEntity, String> {
}
