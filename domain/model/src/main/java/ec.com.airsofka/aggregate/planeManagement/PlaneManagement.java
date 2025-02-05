package ec.com.airsofka.aggregate.planeManagement;


import ec.com.airsofka.aggregate.planeManagement.events.PlaneCreated;
import ec.com.airsofka.aggregate.planeManagement.values.PlaneManagementId;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.utils.AggregateRoot;
import ec.com.airsofka.plane.Plane;
import ec.com.airsofka.plane.PlaneStatus;
import ec.com.airsofka.plane.values.PlaneId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaneManagement extends AggregateRoot<PlaneManagementId> {
    private Plane plane;

    public PlaneManagement() {
        super(new PlaneManagementId());
        setSubscription(new PlaneManagementHandler(this));
    }

    public PlaneManagement(final String id) {
        super(PlaneManagementId.of(id));
        setSubscription(new PlaneManagementHandler(this));
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void createPlane(PlaneStatus state, String model) {
        addEvent(new PlaneCreated(new PlaneId().getValue(), state, model)).apply();
    }

    public static Mono<PlaneManagement> from(final String id, Flux<DomainEvent> events) {
        PlaneManagement planeManagement = new PlaneManagement(id);

        return events
                .filter(eventsFilter -> id.equals(eventsFilter.getAggregateRootId()))
                .flatMap(event -> Mono.fromRunnable(() -> planeManagement.addEvent(event).apply()))
                .doOnTerminate(planeManagement::markEventsAsCommitted)
                .then(Mono.just(planeManagement));
    }
}
