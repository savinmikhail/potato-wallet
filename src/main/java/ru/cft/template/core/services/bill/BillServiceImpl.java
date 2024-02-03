package ru.cft.template.core.services.bill;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.bill.request.CreateTransactionDTO;
import ru.cft.template.api.dto.bill.response.GetBillDTO;
import ru.cft.template.api.dto.bill.response.GetTransactionDTO;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.core.models.Wallet;
import ru.cft.template.core.repositories.bill.BillRepository;
import ru.cft.template.core.repositories.user.UserRepository;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    public List<GetBillDTO> getBillsByType(UUID userId, Optional<TransactionType> type) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Bill> bills;
        if (type.isPresent()) {
            bills = billRepository.findByUserAndType(user, type.get());
        } else {
            bills = billRepository.findByUser(user);
        }

        return mapBillsToDTO(bills);
    }

    private GetBillDTO mapBillToDTO(Bill bill) {
        GetBillDTO billDTO = new GetBillDTO();
        billDTO.setId(bill.getId());
        billDTO.setUserId(bill.getUser().getId());
        billDTO.setAmount(bill.getAmount());
        billDTO.setTransactionDate(bill.getTransactionDate());
        billDTO.setType(bill.getType());

        billDTO.setReceiverPhone(Optional.ofNullable(bill.getReceiverPhone()));
        billDTO.setMaintenanceNumber(Optional.ofNullable(bill.getMaintenanceNumber()));

        billDTO.setStatus(bill.getStatus());

        return billDTO;
    }



    private List<GetBillDTO> mapBillsToDTO(List<Bill> bills) {
        return bills.stream()
                .map(this::mapBillToDTO)
                .collect(Collectors.toList());
    }

    public GetTransactionDTO createTransaction(CreateTransactionDTO dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(UserNotFoundException::new);
        Bill bill = Bill.builder()
                .user(user)
                .amount(dto.getAmount())
                .transactionDate(new Date())
                .status("successful")
                .build();
        if (dto.getReceiverPhone().isPresent()){
            bill.setType(TransactionType.transfer);
            bill.setReceiverPhone(dto.getReceiverPhone().get());
        }else if(dto.getMaintenanceNumber().isPresent()) {
            bill.setType(TransactionType.payment);
            bill.setMaintenanceNumber(dto.getMaintenanceNumber().get());
        }else{
            throw new IllegalArgumentException("Either receiverPhone or maintenanceNumber must be present");
        }
        billRepository.save(bill);

        return mapTransactionToDTO(bill, user);
    }

    private GetTransactionDTO mapTransactionToDTO(Bill bill, User user){
        GetTransactionDTO dto = new GetTransactionDTO();
        dto.setId(bill.getId());
        dto.setUserId(user.getId());
        dto.setWallet(user.getWallet());
        return  dto;
    }
}
