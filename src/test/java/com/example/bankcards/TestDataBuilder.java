package com.example.bankcards;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestDataBuilder {

    public static List<CardDto> listCardTestData() {
         CardDto first = CardDto.builder()
                 .number("1111-2222-3333-4444")
                 .balance(new BigDecimal(100))
                .build();
         CardDto second = CardDto.builder()
                 .number("2222-3333-4444-5555")
                 .balance(new BigDecimal(200))
                 .build();

         return List.of(first, second);
    }

    public static CreateCardRequest cardRequest() {
        return CreateCardRequest.builder()
                .number("4321-8765-2109-6543")
                .expiresAt(LocalDate.now().plusYears(2))
                .initialBalance(BigDecimal.ZERO)
                .ownerUsername("Ivan")
                .build();
    }

    public static CardDto createdCard() {
        return CardDto.builder()
                .id(1L)
                .number("4321-8765-2109-6543")
                .expiresAt(LocalDate.now().plusYears(2))
                .balance(BigDecimal.ZERO)
                .ownerUsername("Ivan")
                .status(CardStatus.ACTIVE)
                .build();
    }
}
