package ec.com.airsofka.generics.utils;

import ec.com.airsofka.generics.domain.DomainActionsContainer;
import ec.com.airsofka.generics.domain.DomainActionsHandler;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.interfaces.IApplyEvent;

import java.util.List;

public abstract class AggregateRoot<I extends Identity> extends Entity<I> {
    private final DomainActionsHandler actionsHandler= new DomainActionsHandler();

    protected AggregateRoot(final I id) {
        super(id);
    }

    public List<DomainEvent> getUncommittedEvents() {
        return List.copyOf(actionsHandler.getEvents());
    }

    public void markEventsAsCommitted() {
        actionsHandler.getEvents().clear();
    }

    protected void setSubscription(final DomainActionsContainer container) {
        actionsHandler.subscribe(container);
    }

    protected IApplyEvent addEvent(final DomainEvent event) {
        final String aggregateName = this.getId()
                .getClass()
                .getSimpleName()
                .replace("Id", "")
                .toLowerCase();
        event.setAggregateRootId(getId().getValue());
        event.setAggregateRootName(aggregateName);

        return actionsHandler.append(event);
    }
}
