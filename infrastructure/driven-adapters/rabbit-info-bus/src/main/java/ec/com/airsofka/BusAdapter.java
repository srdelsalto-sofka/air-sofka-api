package ec.com.airsofka;

import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.generics.domain.DomainEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BusAdapter implements BusEvent {
    @Override
    public void sendEventFlightCreated(Mono<DomainEvent> event) {
        event.subscribe();
    }
}
