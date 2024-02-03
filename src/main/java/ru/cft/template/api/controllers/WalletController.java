package ru.cft.template.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.wallet.request.HesoyamDTO;
import ru.cft.template.api.dto.wallet.response.GetWalletDTO;
import ru.cft.template.api.dto.wallet.response.GetHesoyamDTO;
import ru.cft.template.core.services.wallet.WalletService;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "wallet", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WalletController {
    private final WalletService service;

    @GetMapping(value = "/bill/{id}")
    public ResponseEntity<GetWalletDTO> getWalletById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.getWallet(id), HttpStatus.OK) ;
    }

    @PostMapping(value = "/hesoyam/{id}")
    public ResponseEntity<GetHesoyamDTO> hesoyam(@PathVariable UUID id, @Valid @RequestBody HesoyamDTO dto) {
        return new ResponseEntity<>(service.hesoyam(id, dto), HttpStatus.OK) ;
    }
}
