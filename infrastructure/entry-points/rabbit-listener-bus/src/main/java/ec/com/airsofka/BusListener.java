package ec.com.airsofka;

import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.commands.usecases.SendEmailUseCase;
import ec.com.airsofka.gateway.BusEventListener;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.rabbit.RabbitProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusListener  implements BusEventListener {
    private  final RabbitProperties rabbitProperties;
    private final SendEmailUseCase sendEmailUseCase;

    public BusListener(RabbitProperties rabbitProperties, SendEmailUseCase sendEmailUseCase) {
        this.rabbitProperties = rabbitProperties;
        this.sendEmailUseCase = sendEmailUseCase;
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
                .doOnSuccess(value ->System.out.println("Send email result: " + value))
                .subscribe();
    }
}
