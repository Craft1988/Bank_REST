package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardStatusRequest {
    private CardStatus status;
}
