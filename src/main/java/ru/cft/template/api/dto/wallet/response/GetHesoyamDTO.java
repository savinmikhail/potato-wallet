package ru.cft.template.api.dto.wallet.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GetHesoyamDTO {
    private UUID billId;
    private Long amount;
}
