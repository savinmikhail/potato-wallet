package ru.cft.template.api.dto.wallet.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GetWalletDTO {
    private UUID id;
    private Long amount;
    @JsonFormat(pattern="dd-mm-yyyy")
    private Date lastUpdate;
}
