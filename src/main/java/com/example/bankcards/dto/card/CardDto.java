package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CardDto {
    private Long id;
    private String number;
    private LocalDate expiresAt;
    private CardStatus status;
    private BigDecimal balance;
    private String ownerUsername;
}
