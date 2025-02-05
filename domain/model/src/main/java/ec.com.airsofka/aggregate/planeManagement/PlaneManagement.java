package ec.com.airsofka.aggregate.planeManagement;

import ec.com.airsofka.aggregate.planeManagement.values.PlaneManagementId;
import ec.com.airsofka.generics.utils.AggregateRoot;
import ec.com.airsofka.plane.Plane;

public class PlaneManagement extends AggregateRoot<PlaneManagementId> {
    private Plane plane;

    protected PlaneManagement(PlaneManagementId id) {
        super(id);
    }
}
