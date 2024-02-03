package ru.cft.template.api.dto.bill.response;

import lombok.Data;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.Wallet;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
public class GetTransactionDTO {
    private UUID id;
    private UUID userId;
    private Wallet wallet;
}
