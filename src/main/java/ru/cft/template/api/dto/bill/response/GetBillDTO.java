package ru.cft.template.api.dto.bill.response;

import lombok.Data;
import ru.cft.template.core.enums.TransactionType;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
public class GetBillDTO {
    private UUID id;
    private UUID userId;
    private Long amount;//pennies
    private Date transactionDate;
    private TransactionType type;//"transfer/payment"
    private Optional<Long> receiverPhone;// optional. Used for type = transfer
    private Optional<Long> maintenanceNumber;// optional. Used for type = payment,
    private String status;//"successful"
}
