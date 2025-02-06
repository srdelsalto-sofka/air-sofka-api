package ec.com.airsofka.maintenance.commands.usecases;

import ec.com.airsofka.gateway.BusEvent;
import ec.com.airsofka.gateway.IEventStore;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.maintenance.commands.CreateMaintenanceCommand;
import ec.com.airsofka.maintenance.queries.responses.MaintenanceResponse;
import reactor.core.publisher.Mono;

public class CreateMaintenanceUseCase implements IUseCaseExecute<CreateMaintenanceCommand, MaintenanceResponse> {
    private final IEventStore repository;
    private final BusEvent busEvent;

    public CreateMaintenanceUseCase(IEventStore repository, BusEvent busEvent) {
        this.repository = repository;
        this.busEvent = busEvent;
    }

    @Override
    public Mono<MaintenanceResponse> execute(CreateMaintenanceCommand cmd) {
        return null;
    }
}
