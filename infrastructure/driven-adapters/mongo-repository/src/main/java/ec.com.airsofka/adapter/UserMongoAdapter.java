package ec.com.airsofka.adapter;

import ec.com.airsofka.gateway.IUserRepository;
import ec.com.airsofka.gateway.dto.UserDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserMongoAdapter  implements IUserRepository {


    @Override
    public Mono<UserDTO> save(UserDTO usertDTO) {
        return null;
    }
}
