package ec.com.airsofka.user.commands.usecases;

import ec.com.airsofka.aggregate.auth.Auth;
import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.user.User;
import ec.com.airsofka.user.commands.CreateUserCommand;
import ec.com.airsofka.user.queries.responses.UserResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class CreateUserUsecase implements IUseCaseExecute<CreateUserCommand, UserResponse>{

    private final IEventStore eventRepository;

    private final BusEvent busEvent;

    public CreateUserUsecase(IEventStore eventRepository, BusEvent busEvent) {
        this.eventRepository = eventRepository;
        this.busEvent = busEvent;
    }


    @Override
    public Mono<UserResponse> execute(CreateUserCommand cmd) {

        Auth authAggregate = new Auth();

        authAggregate.createUser(cmd.getBirthDate(),
                cmd.getDocumentNumber(),
                cmd.getDocumentType(),
                cmd.getEmail(),
                cmd.getFirstLastName(),
                cmd.getFrequent(),
                cmd.getLastLastName(),
                cmd.getName(),
                cmd.getNumberOfFlights(),
                cmd.getPassword(),
                cmd.getPhone(),
                cmd.getPrefix(),
                cmd.getRole(),
                cmd.getTitle());


        authAggregate.getUncommittedEvents()
                .stream()
                .map(eventRepository::save)
                .forEach(busEvent::sendEventUserCreated);

        User newUser = authAggregate.getUser();

        authAggregate.markEventsAsCommitted();

        return Mono.just(new UserResponse(
                newUser.getId().getValue(),
                newUser.getBirthDate().getValue(),
                newUser.getDocumentNumber().getValue(),
                newUser.getDocumentType(),
                newUser.getEmail().getValue(),
                newUser.getFirstLastName().getValue(),
                newUser.getFrequent().getValue(),
                newUser.getLastLastName().getValue(),
                newUser.getName().getValue(),
                newUser.getNumberOfFlights().getValue(),
                newUser.getPassword().getValue(),
                newUser.getPhone().getValue(),
                newUser.getPrefix().getValue(),
                newUser.getRole(),
                newUser.getTitle().getValue()
        ));
    }
}
