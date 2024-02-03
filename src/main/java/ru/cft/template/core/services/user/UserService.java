package ru.cft.template.core.services.user;

import ru.cft.template.api.dto.user.request.RegisterDTO;
import ru.cft.template.api.dto.user.request.UpdateDTO;
import ru.cft.template.api.dto.user.response.GetUserDTO;
import ru.cft.template.core.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> get();
    GetUserDTO getUserById(UUID id);
    GetUserDTO update(UUID id, UpdateDTO dto);

    void register(RegisterDTO dto);
}
