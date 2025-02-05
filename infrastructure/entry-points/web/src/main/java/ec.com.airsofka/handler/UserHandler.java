package ec.com.airsofka.handler;

import ec.com.airsofka.user.commands.usecases.CreateUserUsecase;

public class UserHandler {

    private final CreateUserUsecase usecase;

    public UserHandler(CreateUserUsecase usecase) {
        this.usecase = usecase;
    }


}
