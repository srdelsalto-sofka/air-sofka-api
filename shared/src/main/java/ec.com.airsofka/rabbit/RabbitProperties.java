package ec.com.airsofka.rabbit;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:rabbit-application.properties")
public class RabbitProperties {

    @Value("${booking.exchange.name}")
    private String bookingExchange;

    @Value("${booking.queue.name}")
    private String bookingQueue;

    @Value("${booking.routing.key}")
    private String bookingRoutingKey;

    @Value("${flightCreated.exchange.name}")
    private String flightCreatedExchange;

    @Value("${flightCreated.queue.name}")
    private String flightCreatedQueue;

    @Value("${flightCreated.routing.key}")
    private String flightCreatedRoutingKey;

    @Value("${email.exchange.name}")
    private String emailExchange;

    @Value("${email.queue.name}")
    private String emailQueue;

    @Value("${email.routing.key}")
    private String emailRoutingKey;

    public String getBookingExchange() {
        return bookingExchange;
    }

    public String getBookingQueue() {
        return bookingQueue;
    }

    public String getBookingRoutingKey() {
        return bookingRoutingKey;
    }

    public String getFlightCreatedExchange() {
        return flightCreatedExchange;
    }

    public String getFlightCreatedQueue() {
        return flightCreatedQueue;
    }

    public String getFlightCreatedRoutingKey() {
        return flightCreatedRoutingKey;
    }

    public String getEmailExchange() {
        return emailExchange;
    }

    public String getEmailQueue() {
        return emailQueue;
    }

    public String getEmailRoutingKey() {
        return emailRoutingKey;
    }

    public String[] getAllQueues() {
        return new String[]{getBookingQueue(), getFlightCreatedQueue(), getEmailQueue()};
    }
}
