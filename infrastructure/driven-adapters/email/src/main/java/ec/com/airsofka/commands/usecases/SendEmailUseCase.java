package ec.com.airsofka.commands.usecases;


import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.data.Seat;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.queries.responses.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendEmailUseCase implements IUseCaseExecute<SendEmailCommand, EmailResponse> {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SendEmailUseCase(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public Mono<EmailResponse> execute(SendEmailCommand cmd) {
        return Mono.fromRunnable(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);


                Context context = new Context();
                context.setVariables(buildVariables());
                String htmlContent = templateEngine.process("booking-confirmation", context);

                helper.setTo(cmd.getTo());
                helper.setSubject("Your Flight Booking Confirmation");
                helper.setText(htmlContent, true);

                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Error sending email", e);
            }
        });
    }

    private Map<String, Object> buildVariables(){
        Map<String, Object> variables = new HashMap<>();
        variables.put("confirmationTitle", "Booking Confirmation");
        variables.put("bookingDate", "Sunday, December 10, 2023");
        variables.put("email", "test@test.com");
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
        //Taxes
        variables.put("ticketPrice", "$150");  //calculate
        variables.put("airportTax", "$10");
        variables.put("additionalCharges", "$65");
        variables.put("fuelInsurance", "$25");
        variables.put("bookingFee", "$5");
        variables.put("totalAmount", "$255");

        variables.put("keyNotes", "Please bring this confirmation along with an ID.");

        List<Seat> availableSeats = new ArrayList<>();
        availableSeats.add(new Seat("Premium Economy", "5B", "John Doe"));
        availableSeats.add(new Seat("Economy", "7B", "John Two"));
        availableSeats.add(new Seat("Business", "1A", "Alice Smith"));

        List<Map<String, String>> seats = availableSeats.stream()
                .map(seat -> {
                    Map<String, String> seatData = new HashMap<>();
                    seatData.put("flightClass", seat.flightClass());
                    seatData.put("seatNumber", seat.seatNumber());
                    seatData.put("passengerName", seat.passengerName());
                    return seatData;
                })
                .toList();

        variables.put("seats", seats);
        return variables;

    }
}

