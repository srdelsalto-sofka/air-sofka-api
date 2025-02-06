package ec.com.airsofka.mapper;

import ec.com.airsofka.dto.FlightRequestDTO;
import ec.com.airsofka.flight.commands.CreateFlightCommand;

public class FlightMapper {

    public static CreateFlightCommand toCommand(FlightRequestDTO flightRequestDTO) {
        return new CreateFlightCommand(
                flightRequestDTO.getOrigin(),
                flightRequestDTO.getDestination(),
                flightRequestDTO.getDeparture(),
                flightRequestDTO.getArrival(),
                flightRequestDTO.getPrice(),
                flightRequestDTO.getIdPlane()
        );
    }
}
