package ec.com.airsofka;

import ec.com.airsofka.aggregate.auth.events.UserCreated;
import ec.com.airsofka.aggregate.auth.events.UserUpdated;
import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.aggregate.planeManagement.events.PlaneCreated;
import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.commands.usecases.SendEmailUseCase;
import ec.com.airsofka.flight.queries.usecases.FlightSavedViewUseCase;
import ec.com.airsofka.gateway.BusEventListener;
import ec.com.airsofka.gateway.dto.FlightDTO;
import ec.com.airsofka.gateway.dto.PlaneDTO;
import ec.com.airsofka.gateway.dto.UserDTO;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.plane.queries.usecases.PlaneSavedViewUseCase;
import ec.com.airsofka.rabbit.rabbit.RabbitProperties;
import ec.com.airsofka.user.queries.usecases.UserSavedViewUseCase;
import ec.com.airsofka.user.queries.usecases.UserUpdatedViewUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusListener implements BusEventListener {
    private final RabbitProperties rabbitProperties;
    private final SendEmailUseCase sendEmailUseCase;
    private final FlightSavedViewUseCase flightSavedViewUseCase;
    private final UserSavedViewUseCase userSavedViewUseCase;
    private final UserUpdatedViewUseCase userUpdatedViewUseCase;
    private final PlaneSavedViewUseCase planeSavedViewUseCase;

    public BusListener(RabbitProperties rabbitProperties, SendEmailUseCase sendEmailUseCase, FlightSavedViewUseCase flightSavedViewUseCase, UserSavedViewUseCase userSavedViewUseCase, UserUpdatedViewUseCase userUpdatedViewUseCase, PlaneSavedViewUseCase planeSavedViewUseCase) {
        this.rabbitProperties = rabbitProperties;
        this.sendEmailUseCase = sendEmailUseCase;
        this.flightSavedViewUseCase = flightSavedViewUseCase;
        this.userSavedViewUseCase = userSavedViewUseCase;
        this.userUpdatedViewUseCase = userUpdatedViewUseCase;
        this.planeSavedViewUseCase = planeSavedViewUseCase;
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getBookingQueue()}")
    public void receiveBookingCreated(DomainEvent emailDetails) {

        System.out.println("WHEN");
        System.out.println(emailDetails.getWhen());


    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getFlightCreatedQueue()}")
    public void receiveFlightCreated(DomainEvent flightCreated) {
        FlightCreated flight = (FlightCreated) flightCreated;

        FlightDTO flightDTO = new FlightDTO(
                flight.getId(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDeparture(),
                flight.getArrival(),
                flight.getPrice(),
                flight.getIdPlane()
        );

        flightSavedViewUseCase.accept(flightDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getUserCreatedQueue()}")
    public void receiveUserCreated(DomainEvent userCreated) {
        UserCreated user = (UserCreated) userCreated;

        UserDTO userDTO = new UserDTO(
                user.getUserId(),
                user.getBirthDate(),
                user.getDocumentNumber(),
                user.getDocumentType(),
                user.getEmail(),
                user.getFirstLastName(),
                user.getFrequent(),
                user.getLastLastName(),
                user.getName(),
                user.getNumberOfFlights(),
                user.getPassword(),
                user.getPhone(),
                user.getPrefix(),
                user.getRole(),
                user.getTitle()
        );
        userSavedViewUseCase.accept(userDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getUserUpdatedQueue()}")
    public void receiveUserUpdated(DomainEvent userUpdated) {
        UserUpdated user = (UserUpdated) userUpdated;

        UserDTO userDTO = new UserDTO(
                user.getUserId(),
                user.getBirthDate(),
                user.getDocumentNumber(),
                user.getDocumentType(),
                user.getEmail(),
                user.getFirstLastName(),
                user.getFrequent(),
                user.getLastLastName(),
                user.getName(),
                user.getNumberOfFlights(),
                user.getPassword(),
                user.getPhone(),
                user.getPrefix(),
                user.getRole(),
                user.getTitle()
        );
        userUpdatedViewUseCase.accept(userDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getPlaneCreatedQueue()}")
    public void receivePlaneCreated(DomainEvent planeCreated) {
        PlaneCreated plane = (PlaneCreated) planeCreated;

        PlaneDTO planeDTO = new PlaneDTO(
                plane.getId(),
                plane.getState(),
                plane.getModel()
        );

        planeSavedViewUseCase.accept(planeDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getEmailQueue()}")
    public void receiveMailEvent(DomainEvent mailEvent) {
        sendEmailUseCase.execute(new SendEmailCommand("jair.quinonez@sofka.com.co", null))
                .doOnSuccess(value -> System.out.println("Send email"))
                .subscribe();
    }
}

