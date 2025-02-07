package ec.com.airsofka.booking.queries.responses;

public class BookingResponse {
    String message;

    public BookingResponse(String message) {
        this.message = message;
    }

    public String getStatus() {
        return message;
    }

    public void setStatus(String status) {
        this.message = message;
    }
}
