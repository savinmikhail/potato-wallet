package ru.cft.template.core.services.bill;

import ru.cft.template.api.dto.bill.request.CreateTransactionDTO;
import ru.cft.template.api.dto.bill.response.GetBillDTO;
import ru.cft.template.api.dto.bill.response.GetTransactionDTO;
import ru.cft.template.core.enums.TransactionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillService {
    List<GetBillDTO> getBillsByType(UUID userId, Optional<TransactionType> type);
    GetTransactionDTO createTransaction(CreateTransactionDTO dto);
}
