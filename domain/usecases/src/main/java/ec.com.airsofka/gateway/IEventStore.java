package ec.com.airsofka.gateway;

import ec.com.airsofka.generics.domain.DomainEvent;
import reactor.core.publisher.Mono;

public interface IEventStore {
    Mono<DomainEvent> save(DomainEvent event);
}
