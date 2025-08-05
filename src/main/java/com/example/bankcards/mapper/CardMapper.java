package com.example.bankcards.mapper;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card fromDto(CreateCardRequest request);

    CardDto fromCard(Card card);
}
