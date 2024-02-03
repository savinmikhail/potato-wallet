package ru.cft.template.api.dto.session.request;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateSessionDTO {
    private Long phone;
    private String password;
}
