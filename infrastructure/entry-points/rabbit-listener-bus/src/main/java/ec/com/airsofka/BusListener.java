package ec.com.airsofka;

import ec.com.airsofka.aggregate.auth.events.UserCreated;
import ec.com.airsofka.aggregate.auth.events.UserUpdated;
import ec.com.airsofka.aggregate.flightOperation.events.FlightCreated;
import ec.com.airsofka.aggregate.flightOperation.events.SeatListCreated;
import ec.com.airsofka.aggregate.flightOperation.events.SeatReserved;
import ec.com.airsofka.aggregate.planeManagement.events.MaintenanceCreated;
import ec.com.airsofka.aggregate.planeManagement.events.PlaneCreated;
import ec.com.airsofka.aggregate.planeManagement.events.PlaneUpdated;
import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.commands.usecases.SendEmailUseCase;
import ec.com.airsofka.config.RabbitProperties;
import ec.com.airsofka.flight.queries.usecases.FlightSavedViewUseCase;
import ec.com.airsofka.gateway.BusEventListener;
import ec.com.airsofka.gateway.dto.FlightDTO;
import ec.com.airsofka.gateway.dto.MaintenanceDTO;
import ec.com.airsofka.gateway.dto.PlaneDTO;
import ec.com.airsofka.gateway.dto.SeatDTO;
import ec.com.airsofka.gateway.dto.UserDTO;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.maintenance.queries.usecases.MaintenanceSavedViewUseCase;
import ec.com.airsofka.plane.queries.usecases.PlaneSavedViewUseCase;
import ec.com.airsofka.seat.SeatCreatedDTO;
import ec.com.airsofka.seat.queries.usecases.SeatListSavedViewUseCase;
import ec.com.airsofka.user.queries.usecases.UserSavedViewUseCase;
import ec.com.airsofka.user.queries.usecases.UserUpdatedViewUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusListener implements BusEventListener {
    private final RabbitProperties rabbitProperties;
    private final SendEmailUseCase sendEmailUseCase;
    private final FlightSavedViewUseCase flightSavedViewUseCase;
    private final UserSavedViewUseCase userSavedViewUseCase;
    private final UserUpdatedViewUseCase userUpdatedViewUseCase;
    private final PlaneSavedViewUseCase planeSavedViewUseCase;
    private final SeatListSavedViewUseCase seatListSavedViewUseCase;
    private final MaintenanceSavedViewUseCase maintenanceSavedViewUseCase;

    public BusListener(
            RabbitProperties rabbitProperties,
            SendEmailUseCase sendEmailUseCase,
            FlightSavedViewUseCase flightSavedViewUseCase,
            UserSavedViewUseCase userSavedViewUseCase,
            UserUpdatedViewUseCase userUpdatedViewUseCase,
            PlaneSavedViewUseCase planeSavedViewUseCase,
            SeatListSavedViewUseCase seatListSavedViewUseCase,
            MaintenanceSavedViewUseCase maintenanceSavedViewUseCase
    ) {
        this.rabbitProperties = rabbitProperties;
        this.sendEmailUseCase = sendEmailUseCase;
        this.flightSavedViewUseCase = flightSavedViewUseCase;
        this.userSavedViewUseCase = userSavedViewUseCase;
        this.userUpdatedViewUseCase = userUpdatedViewUseCase;
        this.planeSavedViewUseCase = planeSavedViewUseCase;
        this.seatListSavedViewUseCase = seatListSavedViewUseCase;
        this.maintenanceSavedViewUseCase = maintenanceSavedViewUseCase;
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getBookingQueue()}")
    public void receiveBookingCreated(DomainEvent emailDetails) {

        System.out.println("WHEN");
        System.out.println(emailDetails.getWhen());

        Map<String, Object> variables = new HashMap<>();
        variables.put("confirmationTitle", "Air-Sofka Booking Confirmation");
        variables.put("bookingDate", "Sunday, December 10, 2023");
        variables.put("email", "test@test.com"); //mine
        variables.put("phoneNumber", "+1345 6789012");
        variables.put("passengerName", "Mr. James Thompson");
        variables.put("departureCity", "Los Angeles");
        variables.put("arrivalCity", "Paris");
        variables.put("airline", "SkyHigh");
        variables.put("departureDate", "Thursday, December 27, 2023 07:15");
        variables.put("arrivalDate", "Sunday, December 30, 2023 10:15");
        variables.put("flightNumber", "SH1234");
        variables.put("departureTerminal", "Terminal 2");
        variables.put("arrivalTerminal", "Terminal 4A");
        variables.put("ticketPrice", "$150");
        variables.put("airportTax", "$10");
        variables.put("additionalCharges", "$65");
        variables.put("fuelInsurance", "$25");
        variables.put("bookingFee", "$5");
        variables.put("totalAmount", "$255");
        variables.put("keyNotes", "Please bring this confirmation along with an ID.");
        variables.put("seats", List.of(
                Map.of("flightClass", "Premium Economy", "seatNumber", "5B", "passengerName", "John Doe"),
                Map.of("flightClass", "Economy", "seatNumber", "7B", "passengerName", "John Two")
        ));

        sendEmailUseCase.execute(new SendEmailCommand("qtandres17@gmail.com", variables))
                .doOnSuccess(value -> System.out.println("Send email result: " + value))
                .subscribe();
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
    @RabbitListener(queues = "#{rabbitProperties.getMaintenanceQueue()}")
    public void receiveMaintenanceCreated(DomainEvent maintenanceCreated) {
        MaintenanceCreated maintenance = (MaintenanceCreated) maintenanceCreated;

        MaintenanceDTO maintenanceDTO = new MaintenanceDTO(
                maintenance.getId(),
                maintenance.getStart(),
                maintenance.getEnd(),
                maintenance.getIdPlane()
        );

        maintenanceSavedViewUseCase.accept(maintenanceDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getPlaneUpdatedQueue()}")
    public void receivePlaneUpdated(DomainEvent planeUpdated) {
        PlaneUpdated plane = (PlaneUpdated) planeUpdated;

        PlaneDTO planeDTO = new PlaneDTO(
                plane.getId(),
                plane.getState(),
                plane.getModel()
        );

        planeSavedViewUseCase.accept(planeDTO);
    }

    @Override
    @RabbitListener(queues = "#{rabbitProperties.getSeatCreatedQueue()}")
    public void receiveSeatCreated(DomainEvent seatCreated) {
        SeatListCreated seats = (SeatListCreated) seatCreated;

        for (SeatCreatedDTO seat : seats.getSeats()) {
            SeatDTO seatDTO = new SeatDTO(
                    seat.getId(),
                    seat.getNumber(),
                    seat.getRow(),
                    seat.getColumn(),
                    seat.getType(),
                    seat.getStatus(),
                    seat.getPrice(),
                    seat.getIdFlight()
            );

            seatListSavedViewUseCase.accept(seatDTO);
        }
    }

    @RabbitListener(queues = "#{rabbitProperties.getSeatReservedQueue()}")
    public void receiveSeatReserved(DomainEvent seatListUpdated) {
        SeatReserved seat = (SeatReserved) seatListUpdated;

        SeatDTO seatDTO = new SeatDTO(
                seat.getSeatId(),
                seat.getNumber(),
                seat.getRow(),
                seat.getColumn(),
                seat.getType(),
                seat.getStatus(),
                seat.getPrice(),
                seat.getIdFlight()
        );

        seatListSavedViewUseCase.accept(seatDTO);

    }
}
