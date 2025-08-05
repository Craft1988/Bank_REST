package com.example.bankcards.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Исключение, используемое для передачи информации об ошибке
 * в REST API с соответствующим HTTP-статусом.
 */
@Getter
@Setter
public class BankRestException extends RuntimeException {

    /**
     * HTTP-статус, соответствующий данной ошибке.
     */
    private HttpStatus httpStatus;

    /**
     * Создает новое исключение с заданным HTTP-статусом и сообщением.
     *
     * @param httpStatus HTTP-статус, описывающий тип ошибки (например, 404, 400, 500)
     * @param message подробное сообщение об ошибке
     */
    public BankRestException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

