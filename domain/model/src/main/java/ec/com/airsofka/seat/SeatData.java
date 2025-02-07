package ec.com.airsofka.seat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SeatData {
    public static List<SeatCreatedDTO> getSeatList(String idFlight) {
        List<SeatCreatedDTO> seats = new ArrayList<>();
        int rowNumber = 1;

        // FIRST CLASS (2 columns per row, 2 rows)
        for (int i = 0; i < 2; i++, rowNumber++) {
            seats.add(new SeatCreatedDTO("F" + rowNumber + "A", rowNumber, "A", SeatClass.FIRST, SeatStatus.AVAILABLE, new BigDecimal("500.00"), idFlight));
            seats.add(new SeatCreatedDTO("F" + rowNumber + "B", rowNumber, "B", SeatClass.FIRST, SeatStatus.AVAILABLE, new BigDecimal("500.00"), idFlight));
        }

        // BUSINESS CLASS (4 columns per row, 3 rows)
        for (int i = 0; i < 3; i++, rowNumber++) {
            seats.add(new SeatCreatedDTO("B" + rowNumber + "A", rowNumber, "A", SeatClass.BUSINESS, SeatStatus.AVAILABLE, new BigDecimal("300.00"), idFlight));
            seats.add(new SeatCreatedDTO("B" + rowNumber + "B", rowNumber, "B", SeatClass.BUSINESS, SeatStatus.AVAILABLE, new BigDecimal("300.00"), idFlight));
            seats.add(new SeatCreatedDTO("B" + rowNumber + "C", rowNumber, "C", SeatClass.BUSINESS, SeatStatus.AVAILABLE, new BigDecimal("300.00"), idFlight));
            seats.add(new SeatCreatedDTO("B" + rowNumber + "D", rowNumber, "D", SeatClass.BUSINESS, SeatStatus.AVAILABLE, new BigDecimal("300.00"), idFlight));
        }

        // ECONOMY CLASS (6 columns per row, 5 rows)
        for (int i = 0; i < 5; i++, rowNumber++) {
            for (char col = 'A'; col <= 'F'; col++) {
                seats.add(new SeatCreatedDTO("E" + rowNumber + col, rowNumber, String.valueOf(col), SeatClass.ECONOMY, SeatStatus.AVAILABLE, new BigDecimal("100.00"), idFlight));
            }
        }

        return seats;
    }
}
