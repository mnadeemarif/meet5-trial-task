package com.meet5.task.config;

import com.meet5.task.exception.FraudulentActivityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException e) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(FraudulentActivityException.class)
    public ResponseEntity<?> handleFraudulentException(FraudulentActivityException e) {
        return getErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception e) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<?> getErrorResponse(HttpStatus status, String message) {
        log.info("Exception occurred with message : {}", message);
        return new ResponseEntity<>(Map.of("message", message, "success", false), status);
    }

}
