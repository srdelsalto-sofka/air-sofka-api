package ec.com.airsofka.database.airsofka;

import ec.com.airsofka.data.SeatEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SeatMongoRepository extends ReactiveMongoRepository<SeatEntity, String> {
}
