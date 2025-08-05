package com.example.bankcards.dto.transaction;

import com.example.bankcards.entity.enums.TransactionStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(description = "Данные о транзакции")
public class TransactionResponseDto {

    @Schema(description = "Уникальный идентификатор транзакции", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID txUuid;

    @Schema(description = "ID карты, с которой осуществляется перевод", example = "1234567890123456")
    private Long fromCardId;

    @Schema(description = "ID карты, на которую осуществляется перевод", example = "6543210987654321")
    private Long toCardId;

    @Schema(description = "Сумма транзакции", example = "1500.75")
    private BigDecimal amount;

    @Schema(description = "Статус транзакции", example = "COMPLETED")
    private TransactionStatus status;

    @Schema(description = "Дата и время создания транзакции", example = "2025-08-04T09:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Дата и время завершения транзакции", example = "2025-08-04T09:05:00")
    private LocalDateTime completedAt;
}
