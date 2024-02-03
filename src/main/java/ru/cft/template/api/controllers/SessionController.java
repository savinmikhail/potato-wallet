package ru.cft.template.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.session.request.CreateSessionDTO;
import ru.cft.template.api.dto.session.response.GetCreatedSessionDTO;
import ru.cft.template.api.dto.session.response.GetSessionDTO;
import ru.cft.template.api.dto.user.request.RegisterDTO;
import ru.cft.template.api.dto.user.response.GetUserDTO;
import ru.cft.template.core.services.session.SessionService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "users", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping(value = "/{userId}/sessions")
    public ResponseEntity<List<GetSessionDTO>> getSessionsByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(sessionService.getSessionsByUserId(userId), HttpStatus.OK) ;
    }

    @GetMapping(value = "/{userId}/sessions/current")
    public ResponseEntity<GetSessionDTO> getCurrentSessionByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(sessionService.getCurrentSessionByUserId(userId), HttpStatus.OK) ;
    }

    @PostMapping(value = "/sessions")
    public ResponseEntity<GetCreatedSessionDTO> create(@Valid @RequestBody CreateSessionDTO dto) {
        return new ResponseEntity<>(sessionService.create(dto), HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/sessions/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable UUID sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
