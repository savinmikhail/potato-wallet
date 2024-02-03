package ru.cft.template.api.dto.maintenance.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMaintenanceDTO {
    private UUID userId;

    @NotNull
    private Long phone;

    @NotNull
    @Max(value = 1000000)
    private Long amount;

    @NotEmpty
    private String comment;
}
