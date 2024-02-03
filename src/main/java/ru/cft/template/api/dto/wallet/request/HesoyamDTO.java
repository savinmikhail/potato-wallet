package ru.cft.template.api.dto.wallet.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HesoyamDTO {
    @NotNull
    @Max(value = 1000000)
    private Long amount;
}
