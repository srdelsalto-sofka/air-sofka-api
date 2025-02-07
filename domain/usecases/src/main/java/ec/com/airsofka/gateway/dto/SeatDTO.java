package ec.com.airsofka.gateway.dto;

import ec.com.airsofka.seat.SeatClass;
import ec.com.airsofka.seat.SeatStatus;

import java.math.BigDecimal;

public class SeatDTO {
    private String id;
    private String number;
    private Integer row;
    private String column;
    private SeatClass type;
    private SeatStatus status;
    private BigDecimal price;
    private String flightId;

    public SeatDTO(String id, String number, Integer row, String column, SeatClass type, SeatStatus status, BigDecimal price, String flightId) {
        this.id = id;
        this.number = number;
        this.row = row;
        this.column = column;
        this.type = type;
        this.status = status;
        this.price = price;
        this.flightId = flightId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public SeatClass getType() {
        return type;
    }

    public void setType(SeatClass type) {
        this.type = type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
