package com.example.bankcards.aspect;

import com.example.bankcards.dto.error.ErrorResponseDto;
import com.example.bankcards.exception.BankRestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка кастомного исключения BankRestException.
     *
     * @param ex исключение, содержащее статус и сообщение
     * @return DTO с информацией об ошибке
     */
    @ExceptionHandler(BankRestException.class)
    public ResponseEntity<ErrorResponseDto> handleBankRestException(BankRestException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getHttpStatus().getReasonPhrase(),
                ex.getMessage(),
                ex.getHttpStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        String fieldName = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField();
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponseDto response = new ErrorResponseDto(
                "VALIDATION_ERROR",
                String.format("Ошибка в поле '%s': %s", fieldName, errorMessage),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "NOT_FOUND",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrity() {
        ErrorResponseDto error = new ErrorResponseDto(
                "CONFLICT",
                "Пользователь с таким именем уже существует!",
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponseDto> handlePSQLException(SQLException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                "BAD_REQUEST",
                "Ошибка базы данных: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
