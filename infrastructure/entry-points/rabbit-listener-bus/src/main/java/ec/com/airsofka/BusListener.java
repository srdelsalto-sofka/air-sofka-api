package ec.com.airsofka;

import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.commands.usecases.SendEmailUseCase;
import ec.com.airsofka.flight.queries.usecases.FlightSavedViewUseCase;
import ec.com.airsofka.gateway.BusEventListener;
import ec.com.airsofka.gateway.dto.FlightDTO;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.rabbit.RabbitProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusListener implements BusEventListener {
    private final RabbitProperties rabbitProperties;
    private final SendEmailUseCase sendEmailUseCase;
    private final FlightSavedViewUseCase flightSavedViewUseCase;

    public BusListener(RabbitProperties rabbitProperties, SendEmailUseCase sendEmailUseCase, FlightSavedViewUseCase flightSavedViewUseCase) {
        this.rabbitProperties = rabbitProperties;
        this.sendEmailUseCase = sendEmailUseCase;
        this.flightSavedViewUseCase = flightSavedViewUseCase;
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
    @RabbitListener(queues = "#{rabbitProperties.getEmailQueue()}")
    public void receiveMailEvent(DomainEvent mailEvent) {
        sendEmailUseCase.execute(new SendEmailCommand("jair.quinonez@sofka.com.co", null))
                .doOnSuccess(value -> System.out.println("Send email"))
                .subscribe();
    }
}

