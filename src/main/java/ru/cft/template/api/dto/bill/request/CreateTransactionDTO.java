package ru.cft.template.api.dto.bill.request;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class CreateTransactionDTO {
    private UUID userId;
    private Long amount;
    private Optional<Long> receiverPhone;
    private Optional<Long> maintenanceNumber;
}
