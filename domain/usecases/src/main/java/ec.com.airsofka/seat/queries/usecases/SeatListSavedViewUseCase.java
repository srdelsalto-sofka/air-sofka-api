package ec.com.airsofka.seat.queries.usecases;

import ec.com.airsofka.gateway.FlightRepository;
import ec.com.airsofka.gateway.dto.FlightDTO;
import ec.com.airsofka.generics.interfaces.IUseCaseAccept;

public class SeatListSavedViewUseCase implements IUseCaseAccept<SeatDTO, Void> {

    private final SeatRepository seatRepository;

    public SeatListSavedViewUseCase(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public void accept(SeatDTO seatDTO) {
        seatRepository.save(seatDTO).subscribe();
    }
}
