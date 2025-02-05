package ec.com.airsofka.adapter;

import ec.com.airsofka.JSONMap;
import ec.com.airsofka.data.EventEntity;
import ec.com.airsofka.database.events.IEventMongoRepository;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Repository
public class EventMongoAdapter implements IEventStore {

    private final IEventMongoRepository repository;
    private final JSONMap mapper;
    private final ReactiveMongoTemplate eventMongoTemplate;

    public EventMongoAdapter(IEventMongoRepository repository, JSONMap mapper, @Qualifier("eventMongoTemplate") ReactiveMongoTemplate eventMongoTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventMongoTemplate = eventMongoTemplate;
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        EventEntity e = new EventEntity(
                event.getEventId(),
                event.getAggregateRootId(),
                event.getEventType(),
                EventEntity.wrapEvent(event, mapper),
                event.getWhen().toString(),
                event.getVersion(),
                event.getAggregateRootName()
        );
        return repository.save(e)
                .thenReturn(event);
    }

    @Override
    public Flux<DomainEvent> findAggregate(String aggregateId, String aggregate) {
        return repository.findAllByAggregateId(aggregateId)
                .map(eventEntity -> eventEntity.deserializeEvent(mapper, aggregate))
                .sort(Comparator.comparing(DomainEvent::getVersion)
                        .thenComparing(DomainEvent::getWhen));
    }

    @Override
    public Flux<DomainEvent> findAllAggregate(String aggregate) {
        return repository.findAllByAggregateRootName(aggregate)
                .map(eventEntity -> eventEntity.deserializeEvent(mapper, aggregate))
                .sort(Comparator.comparing(DomainEvent::getVersion)
                        .thenComparing(DomainEvent::getWhen));
    }

    @Override
    public Flux<DomainEvent> findAllAggregateByEvent(String aggregate, String eventType) {
        System.out.println("entra0 " + aggregate + eventType);
        return repository.findAllByAggregateRootNameAndEventType(aggregate, eventType)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No se encontraron eventos para los parámetros dados")))
                .doOnError(error -> {
                    System.out.println("error " + error.getMessage());
                })
                .doOnNext(event -> System.out.println("Event AggregateId: " + event.getAggregateId()))
                .map(eventEntity -> eventEntity.deserializeEvent(mapper, aggregate))
                .sort(Comparator.comparing(DomainEvent::getVersion)
                        .thenComparing(DomainEvent::getWhen))
                .onErrorResume(error -> {
                    // Aquí manejas cualquier error globalmente
                    System.err.println("Error durante el proceso: " + error.getMessage());
                    return Mono.error(new RuntimeException("Hubo un error procesando los eventos"));
                });
    }

}
