package ec.com.airsofka.cron;

import ec.com.airsofka.maintenance.queries.usecases.CheckMaintenanceViewUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class MaintenanceCronJob {

    private final CheckMaintenanceViewUseCase checkMaintenanceViewUseCase;
    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceCronJob.class);

    public MaintenanceCronJob(CheckMaintenanceViewUseCase checkMaintenanceViewUseCase) {
        this.checkMaintenanceViewUseCase = checkMaintenanceViewUseCase;
        start();
    }

    public void start() {
        Flux.interval(Duration.ofMinutes(1))
                .flatMap(ignore -> checkMaintenanceViewUseCase.accept())
                .doOnNext(result -> LOGGER.info("Mantenimiento verificado"))
                .doOnError(error -> LOGGER.error("Error ejecutando el cron job de mantenimiento", error))
                .subscribe();
    }
}
