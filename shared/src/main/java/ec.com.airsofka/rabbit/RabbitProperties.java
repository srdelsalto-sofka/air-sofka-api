package ec.com.airsofka.rabbit;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:rabbit-application.properties")
public class RabbitProperties {

    @Value("${booking.exchange.name}")
    private String  bookingExchange;

    @Value("${booking.queue.name}")
    private String  bookingQueue;

    @Value("${booking.routing.key}")
    private String  bookingRoutingKey;


    public String getBookingExchange() {
        return bookingExchange;
    }

    public String getBookingQueue() {
        return bookingQueue;
    }

    public String getBookingRoutingKey() {
        return bookingRoutingKey;
    }

    public String[] getAllQueues(){
        return new String[] {getBookingQueue()};
    }
}
