package ec.com.airsofka.aggregate.planeManagement;

import ec.com.airsofka.aggregate.planeManagement.events.PlaneCreated;
import ec.com.airsofka.generics.domain.DomainActionsContainer;
import ec.com.airsofka.plane.Plane;
import ec.com.airsofka.plane.values.PlaneId;
import ec.com.airsofka.plane.values.objects.Model;
import ec.com.airsofka.plane.values.objects.State;

public class PlaneManagementHandler extends DomainActionsContainer {
    public PlaneManagementHandler(PlaneManagement planeManagement) {
        addDomainActions((PlaneCreated event) -> {
            Plane plane = new Plane(
                    PlaneId.of(event.getId()),
                    State.of(event.getState()),
                    Model.of(event.getModel())
            );

            planeManagement.setPlane(plane);
        });
    }
}
