package com.example.bankcards.service;

import com.example.bankcards.dto.transaction.TransactionResponseDto;
import com.example.bankcards.dto.transaction.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для обработки транзакций между картами.
 */
public interface TransactionService {

    /**
     * Выполняет перевод средств между картами.
     *
     * @param transferRequest объект с данными о переводе (откуда, куда, сумма и пр.)
     * @param userId идентификатор пользователя, инициировавшего перевод
     * @return DTO с информацией о проведенной транзакции
     */
    TransactionResponseDto transfer(TransferRequest transferRequest, Long userId);

    /**
     * Возвращает историю транзакций пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список транзакций, совершенных пользователем
     */
    List<TransactionResponseDto> history(Long userId);

    /**
     * Проверяет корректность перевода: карты должны быть активны,
     * а на карте отправителя должно быть достаточно средств.
     *
     * @param from карта, с которой осуществляется перевод
     * @param to карта, на которую осуществляется перевод
     * @param amount сумма перевода
     * @throws IllegalStateException если хотя бы одна из карт не активна
     * @throws IllegalArgumentException если недостаточно средств на карте отправителя
     */
    default void validateTransfer(Card from, Card to, BigDecimal amount) {
        if (from.getStatus() != CardStatus.ACTIVE ||
                to.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Обе карты должны быть активны");
        }
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }
}

