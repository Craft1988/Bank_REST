package com.example.bankcards.dto.card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateCardRequest {
    private String number;
    private LocalDate expiresAt;
    private BigDecimal initialBalance;
    private String ownerUsername;
}
