package ec.com.airsofka.aggregate.auth;

import ec.com.airsofka.aggregate.auth.events.UserCreated;
import ec.com.airsofka.aggregate.auth.events.UserUpdated;
import ec.com.airsofka.aggregate.auth.values.AuthAggregateId;
import ec.com.airsofka.generics.domain.DomainEvent;
import ec.com.airsofka.generics.utils.AggregateRoot;
import ec.com.airsofka.user.User;
import ec.com.airsofka.user.values.UserId;
import ec.com.airsofka.user.values.objects.DocumentType;
import ec.com.airsofka.user.values.objects.Role;

import java.time.LocalDateTime;
import java.util.List;

public class AuthAggregate extends AggregateRoot<AuthAggregateId> {
    private User user;

    public AuthAggregate() {
        super(new AuthAggregateId());
        setSubscription(new UserHandler(this));
    }

    public AuthAggregate(final String id) {
        super(AuthAggregateId.of(id));
        setSubscription(new UserHandler(this));
    }

    public void createUser(LocalDateTime birthDate, String documentNumber, DocumentType documentType,
                           String email, String firstLastName, Boolean frequent, String lastLastName, String name,
                           Integer numberOfFlights, String password, String phone, String prefix, Role role, String title) {

        addEvent(new UserCreated(new UserId().getValue(), birthDate, documentNumber, documentType, email, firstLastName, frequent,
                lastLastName, name, numberOfFlights, password, phone, prefix, role, title));
    }

    public void updateUser(LocalDateTime birthDate, String documentNumber, DocumentType documentType,
                           String email, String firstLastName, Boolean frequent, String lastLastName, String name,
                           Integer numberOfFlights, String password, String phone, String prefix, Role role, String title) {

        addEvent(new UserUpdated(new UserId().getValue(), birthDate, documentNumber, documentType, email,
                firstLastName, frequent, lastLastName, name, numberOfFlights, password, phone,
                prefix, role, title));
    }


    public static AuthAggregate from(final String id, List<DomainEvent> events) {
        AuthAggregate authAggregate = new AuthAggregate(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .reduce((first, second) -> second)
                .ifPresent(event -> authAggregate.addEvent(event).apply());
        authAggregate.markEventsAsCommitted();
        return authAggregate;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}