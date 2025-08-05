package com.example.bankcards.dto.transaction;

import com.example.bankcards.util.validator.ValidTransfer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Запрос на перевод средств между картами")
@ValidTransfer
public class TransferRequest {

    @NotNull
    @Schema(description = "ID карты отправителя", example = "1234567890123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fromCardId;

    @NotNull
    @Schema(description = "ID карты получателя", example = "6543210987654321", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long toCardId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Сумма должна быть больше нуля")
    @Schema(description = "Сумма перевода", example = "1000.00")
    private BigDecimal amount;
}