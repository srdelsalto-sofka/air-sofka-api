package ec.com.airsofka.rabbit.rabbit;


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

    @Value("${userCreated.exchange.name}")
    private String  userExchange;

    @Value("${userCreated.queue.name}")
    private String  userQueue;

    @Value("${userCreated.routing.key}")
    private String  userRoutingKey;

    @Value("${userUpdated.exchange.name}")
    private String  userUpdatedExchange;

    @Value("${userUpdated.queue.name}")
    private String  userUpdatedQueue;

    @Value("${userUpdated.routing.key}")
    private String  userUpdatedRoutingKey;


    public String getBookingExchange() {
        return bookingExchange;
    }

    public String getBookingQueue() {
        return bookingQueue;
    }

    public String getBookingRoutingKey() {
        return bookingRoutingKey;
    }

    public String getUserCreatedExchange() {
        return userExchange;
    }

    public String getUserCreatedQueue() {
        return userQueue;
    }

    public String getUserCreatedRoutingKey() {
        return userRoutingKey;
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


    public String getUserUpdatedExchange() {
        return userUpdatedExchange;
    }

    public String getUserUpdatedQueue() {
        return userUpdatedQueue;
    }

    public String getUserUpdatedRoutingKey() {
        return userUpdatedRoutingKey;
    }

    public String[] getAllQueues() {
        return new String[]{getBookingQueue(), getFlightCreatedQueue(), getUserCreatedQueue(),getUserUpdatedQueue()};
    }
}
