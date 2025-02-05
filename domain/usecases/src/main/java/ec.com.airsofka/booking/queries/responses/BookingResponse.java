package ec.com.airsofka.booking.queries.responses;

public class BookingResponse {
    String status;

    public BookingResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
