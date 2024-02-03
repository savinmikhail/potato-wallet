package ru.cft.template.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.user.request.RegisterDTO;
import ru.cft.template.api.dto.user.request.UpdateDTO;
import ru.cft.template.api.dto.user.response.GetUserDTO;
import ru.cft.template.core.services.user.UserService;
import ru.cft.template.core.models.User;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "users", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>>  get() {
        return new ResponseEntity<>(service.get(), HttpStatus.OK) ;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK) ;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
            service.register(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @RequestMapping(value = "/{id}",  method = RequestMethod.PATCH)
    public ResponseEntity<GetUserDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateDTO dto){
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }
}
