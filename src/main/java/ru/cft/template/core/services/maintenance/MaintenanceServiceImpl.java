package ru.cft.template.core.services.maintenance;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.bill.request.CreateTransactionDTO;
import ru.cft.template.api.dto.bill.response.GetBillDTO;
import ru.cft.template.api.dto.bill.response.GetTransactionDTO;
import ru.cft.template.api.dto.maintenance.request.CreateMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.GetMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.MaintenanceDTO;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.core.enums.MaintenanceStatus;
import ru.cft.template.core.enums.MaintenanceType;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.models.Maintenance;
import ru.cft.template.core.models.User;
import ru.cft.template.core.repositories.maintenance.MaintenanceRepository;
import ru.cft.template.core.repositories.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final UserRepository userRepository;
    private final MaintenanceRepository maintenanceRepository;


    public List<GetMaintenanceDTO> getByType(UUID userId, Optional<MaintenanceType> type){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Maintenance> maintenances;
        if(type.isPresent()) {
            maintenances = maintenanceRepository.findByUserAndType(user, type.get());
        }else {
            maintenances = maintenanceRepository.findByUser(user);
        }
        return mapMaintenancesToDTO(maintenances);

    }

    private List<GetMaintenanceDTO> mapMaintenancesToDTO(List<Maintenance> maintenances) {
        return maintenances.stream()
                .map(this::mapMaintenanceToDTO)
                .collect(Collectors.toList());
    }


    private GetMaintenanceDTO mapMaintenanceToDTO(Maintenance maintenance) {
        GetMaintenanceDTO dto = new GetMaintenanceDTO();
        dto.setId(maintenance.getId());
        dto.setType(maintenance.getType());
        dto.setAmount(maintenance.getAmount());
        dto.setMaintenanceNumber(maintenance.getMaintenanceNumber());
        dto.setStatus(maintenance.getStatus());
        dto.setTransactionDate(maintenance.getTransactionDate());

        return dto;
    }

    public MaintenanceDTO createMaintenance(CreateMaintenanceDTO dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(UserNotFoundException::new);
        Maintenance maintenance = Maintenance.builder()
                .payerPhone(dto.getPhone())
                .user(user)
                .amount(dto.getAmount())
                .comment(dto.getComment())
                .status(MaintenanceStatus.unpaid)
                .type(MaintenanceType.outbound)
                .maintenanceNumber(generateRandomLong())
                .transactionDate(new Date())
                .build();
        maintenanceRepository.save(maintenance);
        return mapMaintenanceToMaintenanceDTO(maintenance);

    }

    private Long generateRandomLong() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }

    private MaintenanceDTO mapMaintenanceToMaintenanceDTO(Maintenance maintenance){
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(maintenance.getId());
        dto.setMaintenanceNumber(maintenance.getMaintenanceNumber());
        dto.setStatus(maintenance.getStatus());
        return  dto;
    }
}
