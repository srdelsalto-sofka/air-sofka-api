package ec.com.airsofka.passenger;

public class PassengerCreatedDTO {
    private final String title;
    private final String name;
    private final String lastName;
    private final String passengerType;
    private final String seatId;

    public PassengerCreatedDTO(String title, String name, String lastName, String passengerType, String seatId) {
        this.title = title;
        this.name = name;
        this.lastName = lastName;
        this.passengerType = passengerType;
        this.seatId = seatId;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public String getSeatId() {
        return seatId;
    }
}
