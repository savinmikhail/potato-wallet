package ru.cft.template.api.dto.maintenance.response;

import jakarta.persistence.*;
import lombok.Data;
import ru.cft.template.core.enums.MaintenanceStatus;
import ru.cft.template.core.enums.MaintenanceType;

import java.util.Date;
import java.util.UUID;

@Data
public class GetMaintenanceDTO {
    private UUID id;
    private MaintenanceType type;
    private Long amount;
    private Long maintenanceNumber;
    private MaintenanceStatus status;
    private Date transactionDate;
}
