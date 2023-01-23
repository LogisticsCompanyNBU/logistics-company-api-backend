package org.nbu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Builder
    @Getter
    private static class ErrorDTO {
        private String message;
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(EntityDoesNotExistException.class)
    public ResponseEntity<ErrorDTO> handleEntityDoesNotExistException(EntityDoesNotExistException exception) {
        log.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.internalServerError()
                             .body(buildErrorDto(constructMessage(exception)));
    }

    private ResponseEntity<ErrorDTO> buildErrorResponseEntity(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status)
                             .body(buildErrorDto(exception.getMessage()));
    }

    private ErrorDTO buildErrorDto(String errorMessage) {
        return ErrorDTO.builder()
                       .message(errorMessage)
                       .build();
    }

    private String constructMessage(Throwable throwable) {
        return String.format("%s: %s", throwable.getClass()
                                                .toString(),
                             throwable.getMessage());
    }
}
