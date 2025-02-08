package ec.com.airsofka.booking.queries.query;

import ec.com.airsofka.gateway.dto.PassengerDTO;
import ec.com.airsofka.gateway.dto.SeatDTO;
import ec.com.airsofka.generics.utils.Query;

import java.util.List;

public class GetAmountsQuery extends Query {
    private final List<PassengerDTO> passengers;
    private final boolean isFrequent;

    public GetAmountsQuery(List<PassengerDTO> passengers, boolean isFrequent) {
        super(null);
        this.passengers = passengers;
        this.isFrequent = isFrequent;
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public boolean isFrequent() {
        return isFrequent;
    }
}
