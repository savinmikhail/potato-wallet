package ru.cft.template.api.dto.session.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GetSessionDTO {
    private UUID id;

    private UUID userId;

    @JsonFormat(pattern="dd-mm-yyyy")
    private Date expirationTime;

    private Boolean active;
}
