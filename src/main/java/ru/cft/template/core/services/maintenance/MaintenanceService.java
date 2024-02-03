package ru.cft.template.core.services.maintenance;

import ru.cft.template.api.dto.maintenance.request.CreateMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.GetMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.MaintenanceDTO;
import ru.cft.template.core.enums.MaintenanceStatus;
import ru.cft.template.core.enums.MaintenanceType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaintenanceService {
    List<GetMaintenanceDTO> getByType(UUID userId, Optional<MaintenanceType> type);
    MaintenanceDTO createMaintenance(CreateMaintenanceDTO dto);
}
