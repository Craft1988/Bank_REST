package com.example.bankcards.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Структура ошибки, возвращаемая при неудачных запросах")
public class ErrorResponseDto {

    @Schema(description = "Короткий тип ошибки", example = "UNAUTHORIZED")
    private String error;

    @Schema(description = "Сообщение об ошибке", example = "Неверные имя пользователя или пароль")
    private String message;

    @Schema(description = "HTTP статус ошибки", example = "401")
    private int status;

    @Schema(description = "Метка времени, когда произошла ошибка", example = "2025-07-30T14:34:45.123")
    private LocalDateTime timestamp;
}
