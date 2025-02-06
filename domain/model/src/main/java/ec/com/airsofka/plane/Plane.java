package ec.com.airsofka.plane;

import ec.com.airsofka.generics.utils.Entity;
import ec.com.airsofka.plane.values.PlaneId;
import ec.com.airsofka.plane.values.objects.State;

public class Plane extends Entity<PlaneId> {
    private final State state;

    public Plane(PlaneId id, State state) {
        super(id);
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
