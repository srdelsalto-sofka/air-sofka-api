package ec.com.airsofka.seat.commands;

import ec.com.airsofka.generics.utils.Command;
import ec.com.airsofka.seat.SeatClass;
import ec.com.airsofka.seat.SeatStatus;

import java.math.BigDecimal;

public class UpdateSeatStatusCommand extends Command {
    private final String seatId;
    private final String number;
    private final Integer row;
    private final String column;
    private final SeatClass type;
    private final SeatStatus status;
    private final BigDecimal price;
    private final String idFlight;

    public UpdateSeatStatusCommand(String seatId, String number, Integer row, String column, SeatClass type, SeatStatus status, BigDecimal price, String idFlight) {
        super(null);
        this.seatId = seatId;
        this.number = number;
        this.row = row;
        this.column = column;
        this.type = type;
        this.status = status;
        this.price = price;
        this.idFlight = idFlight;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getNumber() {
        return number;
    }

    public Integer getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public SeatClass getType() {
        return type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getIdFlight() {
        return idFlight;
    }
}
