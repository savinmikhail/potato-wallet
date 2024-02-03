package ru.cft.template.api.dto.session.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import ru.cft.template.core.models.User;

import java.util.Date;
import java.util.UUID;

@Data
public class GetCreatedSessionDTO {
    private UUID id;

    private UUID userId;

    private String token;
    @JsonFormat(pattern="dd-mm-yyyy")
    private Date expirationTime;
}
