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
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.services.bill.BillService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping(value = "/history/{userId}")
    public ResponseEntity<List<GetBillDTO>> getBillsByType(
            @RequestParam(name = "type", required = false) Optional<TransactionType> type,
            @PathVariable UUID userId
    ) {
        return new ResponseEntity<>(service.getBillsByType(userId, type), HttpStatus.OK);
    }

    @PostMapping(value = "/transfers") 
    public ResponseEntity<GetTransactionDTO> createTransaction(@Valid CreateTransactionDTO dto){
        return new ResponseEntity<>(service.createTransaction(dto), HttpStatus.OK);
    }
}
