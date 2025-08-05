package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на изменение статуса карты")
public class UpdateCardStatusRequest {

    @NotNull(message = "Статус карты обязателен")
    @Schema(
            description = "Новый статус карты",
            example = "BLOCKED",
            allowableValues = {"ACTIVE", "BLOCKED", "EXPIRED", "PENDING_BLOCK"}
    )
    private CardStatus status;
}
