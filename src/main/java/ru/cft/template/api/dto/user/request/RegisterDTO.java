package ru.cft.template.api.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class RegisterDTO {
    @NotNull(message = "phone required")
    private Long phone;

    @NotEmpty(message = "password required")
    private String password;

    @NotEmpty(message = "{validation.user.firstName.not-empty}")
    private String firstName;

    @NotEmpty(message = "last name required")
    private String lastName;

    @NotEmpty(message = "email required")
    @Email
    private String email;

    @NotNull
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must not exceed 100")
    private int age;
}
