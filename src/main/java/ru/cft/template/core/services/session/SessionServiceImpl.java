package ru.cft.template.core.services.session;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.bill.response.GetBillDTO;
import ru.cft.template.api.dto.session.request.CreateSessionDTO;
import ru.cft.template.api.dto.session.response.GetCreatedSessionDTO;
import ru.cft.template.api.dto.session.response.GetSessionDTO;
import ru.cft.template.api.exceptions.SessionNotFoundException;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.models.Session;
import ru.cft.template.core.models.User;
import ru.cft.template.core.repositories.session.SessionRepository;
import ru.cft.template.core.repositories.user.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public List<GetSessionDTO> getSessionsByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Session>  sessions = sessionRepository.findByUser(user);
        return mapSessionsToDTO(sessions);
    }

    private List<GetSessionDTO> mapSessionsToDTO (List<Session> sessions){
        return sessions.stream()
                .map(this::mapSessionToDTO)
                .collect(Collectors.toList());
    }

    private GetSessionDTO mapSessionToDTO(Session session){
        GetSessionDTO getSessionDTO = new GetSessionDTO();
        getSessionDTO.setId(session.getId());
        getSessionDTO.setUserId(session.getUser().getId());
        getSessionDTO.setExpirationTime(session.getExpirationTime());
        getSessionDTO.setActive(session.getActive());
        return getSessionDTO;
    }

    public GetSessionDTO getCurrentSessionByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Session> sessions = sessionRepository.findLatestActiveByUser(user);

        if (!sessions.isEmpty()) {
            Session latestSession = sessions.get(0);
            return mapSessionToDTO(latestSession);
        } else {
            throw new SessionNotFoundException();
        }
    }


    public GetCreatedSessionDTO create(CreateSessionDTO dto){
        User user = userRepository.findByPhoneAndPassword(dto.getPhone(), dto.getPassword())
                .orElseThrow(UserNotFoundException::new);
        Session session = Session.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .active(true)
                .expirationTime(Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        sessionRepository.save(session);
        return mapSessionToGetCreatedSessionDTO(session);
    }

    private GetCreatedSessionDTO mapSessionToGetCreatedSessionDTO(Session session){
        GetCreatedSessionDTO getCreatedSessionDTO = new GetCreatedSessionDTO();
        getCreatedSessionDTO.setId(session.getId());
        getCreatedSessionDTO.setUserId(session.getUser().getId());
        getCreatedSessionDTO.setToken(session.getToken());
        getCreatedSessionDTO.setExpirationTime(session.getExpirationTime());
        return getCreatedSessionDTO;
    }

    public void deleteSession(UUID sessionId){
        sessionRepository.deleteById(sessionId);
    }
}
