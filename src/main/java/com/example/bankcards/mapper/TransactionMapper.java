package com.example.bankcards.mapper;

import com.example.bankcards.dto.transaction.TransactionResponseDto;
import com.example.bankcards.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponseDto toDto(Transaction transaction);
}
