package ru.cft.template.api.dto.maintenance.response;

import lombok.Data;
import ru.cft.template.core.enums.MaintenanceStatus;
import ru.cft.template.core.enums.MaintenanceType;

import java.util.Date;
import java.util.UUID;

@Data
public class MaintenanceDTO {
    private UUID id;
    private Long maintenanceNumber;
    private MaintenanceStatus status;
}
