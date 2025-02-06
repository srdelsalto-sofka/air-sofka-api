package ec.com.airsofka.gateway;

import ec.com.airsofka.gateway.dto.MaintenanceDTO;
import reactor.core.publisher.Mono;

public interface MaintenanceRepository {
    Mono<MaintenanceDTO> save(MaintenanceDTO maintenanceDTO);
}
