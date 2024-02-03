package ru.cft.template.core.services.session;

import ru.cft.template.api.dto.session.request.CreateSessionDTO;
import ru.cft.template.api.dto.session.response.GetCreatedSessionDTO;
import ru.cft.template.api.dto.session.response.GetSessionDTO;

import java.util.List;
import java.util.UUID;

public interface SessionService {
   List<GetSessionDTO> getSessionsByUserId(UUID userId);
    GetSessionDTO getCurrentSessionByUserId(UUID userId);
    GetCreatedSessionDTO create(CreateSessionDTO dto);
    void deleteSession(UUID sessionId);
}
