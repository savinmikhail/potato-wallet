package ru.cft.template.api.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateDTO {

    @NotEmpty(message = "{validation.user.firstName.not-empty}")
    private String firstName;

    @NotEmpty(message = "last name required")
    private String lastName;

    @NotEmpty(message = "email required")
    @Email
    private String email;

}
