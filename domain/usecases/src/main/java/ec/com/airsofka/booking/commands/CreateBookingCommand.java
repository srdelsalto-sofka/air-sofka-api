package ec.com.airsofka.booking.commands;

import ec.com.airsofka.generics.utils.Command;

public class CreateBookingCommand  extends Command {
    private String status;


    public CreateBookingCommand(String aggregateId, String status) {
        super(aggregateId);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
