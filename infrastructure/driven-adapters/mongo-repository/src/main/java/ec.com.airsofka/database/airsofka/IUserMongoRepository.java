package ec.com.airsofka.database.airsofka;

import ec.com.airsofka.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IUserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {
}
