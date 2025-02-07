package ec.com.airsofka.booking.commands;

import ec.com.airsofka.generics.utils.Command;

import java.math.BigDecimal;
import java.util.List;

public class CreateBookingCommand  extends Command {
    private final String bookingStatus;
    private final BigDecimal bookingPrice;
    private final BigDecimal discount;
    private final String flightId;
    private final String userId;

    private  final String paymentMethod;

    private final String email;
    private final String prefix;
    private final String phoneNumber;
    private final List<CreatePassengerCommand> passengers;

    public CreateBookingCommand(String aggregateId, String bookingStatus, BigDecimal bookingPrice, BigDecimal discount, String flightId, String userId, String paymentMethod, String email, String prefix, String phoneNumber, List<CreatePassengerCommand> passengers) {
        super(aggregateId);
        this.bookingStatus = bookingStatus;
        this.bookingPrice = bookingPrice;
        this.discount = discount;
        this.flightId = flightId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.email = email;
        this.prefix = prefix;
        this.phoneNumber = phoneNumber;
        this.passengers = passengers;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public BigDecimal getBookingPrice() {
        return bookingPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getEmail() {
        return email;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public List<CreatePassengerCommand> getPassengers() {
        return passengers;
    }
}
