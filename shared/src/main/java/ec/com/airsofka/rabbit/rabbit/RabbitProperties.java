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
    private String userExchange;

    @Value("${userCreated.queue.name}")
    private String userQueue;

    @Value("${userCreated.routing.key}")
    private String userRoutingKey;

    @Value("${userUpdated.exchange.name}")
    private String userUpdatedExchange;

    @Value("${userUpdated.queue.name}")
    private String userUpdatedQueue;

    @Value("${userUpdated.routing.key}")
    private String userUpdatedRoutingKey;
    @Value("${planeCreated.exchange.name}")
    private String planeCreatedExchange;

    @Value("${planeCreated.queue.name}")
    private String planeCreatedQueue;

    @Value("${planeCreated.routing.key}")
    private String planeCreatedRoutingKey;


    @Value("${email.exchange.name}")
    private String emailExchange;

    @Value("${email.queue.name}")
    private String emailQueue;

    @Value("${email.routing.key}")
    private String emailRoutingKey;

    @Value("${contact.exchange.name}")
    private String contactExchange;

    @Value("${contact.queue.name}")
    private String contactQueue;

    @Value("${contact.routing.key}")
    private String contactRoutingKey;

    @Value("${billing.exchange.name}")
    private String billingExchange;

    @Value("${billing.queue.name}")
    private String billingQueue;

    @Value("${billing.routing.key}")
    private String billingRoutingKey;

    @Value("${passenger.exchange.name}")
    private String passengerExchange;

    @Value("${passenger.queue.name}")
    private String passengerQueue;

    @Value("${passenger.routing.key}")
    private String passengerRoutingKey;

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

    public String getEmailExchange() {
        return emailExchange;
    }

    public String getEmailQueue() {
        return emailQueue;
    }

    public String getEmailRoutingKey() {
        return emailRoutingKey;
    }

    public String getPlaneCreatedExchange() {
        return planeCreatedExchange;
    }

    public String getPlaneCreatedQueue() {
        return planeCreatedQueue;
    }

    public String getPlaneCreatedRoutingKey() {
        return planeCreatedRoutingKey;
    }

    public String getPassengerRoutingKey() {
        return passengerRoutingKey;
    }

    public String getPassengerQueue() {
        return passengerQueue;
    }

    public String getPassengerExchange() {
        return passengerExchange;
    }

    public String getBillingRoutingKey() {
        return billingRoutingKey;
    }

    public String getBillingQueue() {
        return billingQueue;
    }

    public String getBillingExchange() {
        return billingExchange;
    }

    public String getContactRoutingKey() {
        return contactRoutingKey;
    }

    public String getContactQueue() {
        return contactQueue;
    }

    public String getContactExchange() {
        return contactExchange;
    }

    public String[] getAllQueues() {
        return new String[]{getBookingQueue(),
                getFlightCreatedQueue(),
                getUserCreatedQueue(),
                getPlaneCreatedQueue(),
                getUserUpdatedQueue(),
                getEmailQueue(),
                getBillingQueue(),
                getContactQueue(),
                getPassengerQueue()};
    }
}
