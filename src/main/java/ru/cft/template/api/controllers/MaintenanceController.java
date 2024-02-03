package ru.cft.template.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.bill.request.CreateTransactionDTO;
import ru.cft.template.api.dto.bill.response.GetBillDTO;
import ru.cft.template.api.dto.bill.response.GetTransactionDTO;
import ru.cft.template.api.dto.maintenance.request.CreateMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.GetMaintenanceDTO;
import ru.cft.template.api.dto.maintenance.response.MaintenanceDTO;
import ru.cft.template.api.dto.user.response.GetUserDTO;
import ru.cft.template.core.enums.MaintenanceType;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.services.maintenance.MaintenanceService;
import ru.cft.template.core.services.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "maintenance", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService service;

    @GetMapping(value = "{userId}")
    public ResponseEntity<List<GetMaintenanceDTO>> getByType(
            @RequestParam(name = "type", required = false) Optional<MaintenanceType> type,
            @PathVariable UUID userId
    ) {
        return new ResponseEntity<>(service.getByType(userId, type), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MaintenanceDTO> createMaintenance(@Valid CreateMaintenanceDTO dto){
        return new ResponseEntity<>(service.createMaintenance(dto), HttpStatus.OK);
    }
}
