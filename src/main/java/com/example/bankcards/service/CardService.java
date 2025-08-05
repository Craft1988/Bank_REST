package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.BankRestException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    // ADMIN: создание карты
    public CardDto createCard(CreateCardRequest req) {
        User owner = userRepository.findUserByUsername(req.getOwnerUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Card card = cardMapper.fromDto(req);

        return cardMapper.fromCard(cardRepository.save(card));
    }

    // ADMIN: изменить статус (BLOCKED, ACTIVE)
    public CardDto updateStatus(Long cardId, CardStatus status) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        card.setStatus(status);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    // ADMIN: удаление
    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    // ADMIN: просмотр всех карт с пагинацией
    public Page<CardDto> listAll(Pageable pageable) {
        return cardRepository
                .findAll(pageable)
                .map(cardMapper::fromCard);
    }

    // USER: просмотр своих карт + поиск + пагинация
    public Page<CardDto> listMyCards(String username, String numberFilter, Pageable pageable) {
        return cardRepository
                .findByOwnerUsernameAndNumberContainingIgnoreCase(username, numberFilter, pageable)
                .map(cardMapper::fromCard);
    }

    // USER: запрос на блокировку (механизм заявки)
    public void requestBlock(String username, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        if (!card.getOwner().getUsername().equals(username)) {
            throw new BankRestException(HttpStatus.BAD_REQUEST, "Не ваша карта!");
        }

        card.setStatus(CardStatus.PENDING_BLOCK);
        cardRepository.save(card);
    }

    // USER: посмотреть баланс
    public BigDecimal getBalance(String username, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        if (!card.getOwner().getUsername().equals(username)) {
            throw new BankRestException(HttpStatus.BAD_REQUEST, "Не ваша карта!");
        }
        return card.getBalance();
    }

}

