package com.franchise.infrastructure.adapter.input.controlleradvice;

import com.franchise.application.dto.response.ExceptionResponseDTO;
import com.franchise.application.helper.exception.FranchiseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FranchiseNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerFranchiseNotFoundException(FranchiseNotFoundException e) {
        ExceptionResponseDTO exceptionResponse = ExceptionResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(e.getErrorCode())
                .message(e.getMessage())
                .details(e.getDetails())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponseDTO exceptionResponse = ExceptionResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }
}
