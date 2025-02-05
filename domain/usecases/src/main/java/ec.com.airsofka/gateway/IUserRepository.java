package ec.com.airsofka.gateway;

import ec.com.airsofka.gateway.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface IUserRepository {
    Mono<UserDTO> save(UserDTO usertDTO);

}
