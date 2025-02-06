package ec.com.airsofka.database.airsofka;

import ec.com.airsofka.data.MaintenanceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MaintenanceMongoRepository extends ReactiveMongoRepository<MaintenanceEntity, String> {
}
