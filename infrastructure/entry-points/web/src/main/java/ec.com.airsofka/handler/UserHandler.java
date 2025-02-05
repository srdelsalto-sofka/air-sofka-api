package ec.com.airsofka.handler;

import ec.com.airsofka.user.commands.CreateUserCommand;
import ec.com.airsofka.user.commands.usecases.CreateUserUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final CreateUserUsecase createUserUsecase;

    public UserHandler(CreateUserUsecase createUserUsecase) {
        this.createUserUsecase = createUserUsecase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateUserCommand.class)
                .flatMap(createUserUsecase::execute)
                .flatMap(userResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(userResponse)
                );
    }


}
