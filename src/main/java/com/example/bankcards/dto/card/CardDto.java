package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Информация о карте")
public class CardDto {

    @Schema(description = "Уникальный ID карты", example = "987654321")
    private Long id;

    @Schema(description = "Номер карты в формате XXXX-XXXX-XXXX-XXXX", example = "1234-5678-9012-3456")
    private String number;

    @Schema(description = "Дата истечения срока действия карты", example = "2026-12-31")
    private LocalDate expiresAt;

    @Schema(description = "Статус карты", example = "ACTIVE")
    private CardStatus status;

    @Schema(description = "Баланс на карте", example = "2500.00")
    private BigDecimal balance;

    @Schema(description = "Имя пользователя — владельца карты", example = "mikhail_dev")
    private String ownerUsername;
}
