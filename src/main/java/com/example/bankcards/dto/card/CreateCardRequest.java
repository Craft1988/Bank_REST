package com.example.bankcards.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Запрос на создание новой карты")
public class CreateCardRequest {

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Номер карты должен быть в формате XXXX-XXXX-XXXX-XXXX")
    @Schema(description = "Номер карты", example = "4321-8765-2109-6543")
    private String number;

    @NotNull
    @Future(message = "Дата истечения должна быть в будущем")
    @Schema(description = "Срок действия карты", example = "2027-05-31")
    private LocalDate expiresAt;

    @NotNull
    @DecimalMin(value = "0.00", message = "Баланс не может быть отрицательным")
    @Digits(integer = 10, fraction = 2, message = "Баланс должен быть с максимум двумя знаками после запятой")
    @Schema(description = "Начальный баланс карты", example = "1000.00")
    private BigDecimal initialBalance;

    @NotBlank
    @Size(min = 4, max = 20, message = "Имя пользователя должно быть от 4 до 20 символов")
    @Schema(description = "Имя владельца карты", example = "mikhail_dev")
    private String ownerUsername;
}
