package ru.cft.template.api.controllers;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.api.exceptions.WalletNotFoundException;
import ru.cft.template.api.responses.ValidationErrorResponse;
import ru.cft.template.api.responses.Violation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlingController {

    public final PropertyResolverUtils propertyResolverUtils;

    @Autowired
    public ErrorHandlingController(PropertyResolverUtils propertyResolverUtils) {
        this.propertyResolverUtils = propertyResolverUtils;
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(
                                error.getField(),
                                propertyResolverUtils.resolve(
                                        error.getDefaultMessage(),
                                        Locale.getDefault()
                                )
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(
                                propertyResolverUtils.resolve(
                                        e.getMessage(),
//                                        "api.wallet.server.error",
                                        Locale.getDefault()
                                )
                        )
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                propertyResolverUtils.resolve(
                                        e.getMessage(),
                                        Locale.getDefault()
                                )
                        )
                );
    }

    @ExceptionHandler(WalletNotFoundException.class)//TODO create base not found exception to avoid duplicating of captures
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(
            WalletNotFoundException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                propertyResolverUtils.resolve(
                                        e.getMessage(),
                                        Locale.getDefault()
                                )
                        )
                );
    }

    public record ErrorResponse(String message) {
    }
}
