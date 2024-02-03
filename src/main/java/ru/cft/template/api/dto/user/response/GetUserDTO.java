package ru.cft.template.api.dto.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GetUserDTO {
    private UUID id;
    private UUID walletId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Long phone;
    @JsonFormat(pattern="dd-mm-yyyy")
    private Date registrationDate;
    @JsonFormat(pattern="dd-mm-yyyy")
    private Date lastUpdateDate;
    private int age;
}
