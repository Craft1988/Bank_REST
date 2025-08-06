package com.example.bankcards.service.impl;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.BankRestException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    public CardDto createCard(CreateCardRequest req) {
        User owner = userRepository.findUserByUsername(req.getOwnerUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Card card = cardMapper.fromDto(req);
        card.setOwner(owner);

        return cardMapper.fromCard(cardRepository.save(card));
    }

    public CardDto updateStatus(Long cardId, CardStatus status) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        card.setStatus(status);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteCardById(cardId);
    }

    public Page<CardDto> allCards(Pageable pageable) {
        return cardRepository
                .findAll(pageable)
                .map(cardMapper::fromCard);
    }

    public Page<CardDto> myCards(String username, String numberFilter, Pageable pageable) {
        return cardRepository
                .findByOwnerUsernameAndNumberContainingIgnoreCase(username, numberFilter, pageable)
                .map(cardMapper::fromCard);
    }

    public void blockCard(String username, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        if (!card.getOwner().getUsername().equals(username)) {
            throw new BankRestException(HttpStatus.BAD_REQUEST, "Не ваша карта!");
        }

        card.setStatus(CardStatus.PENDING_BLOCK);
        cardRepository.save(card);
    }

    public BigDecimal balance(String username, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена"));
        if (!card.getOwner().getUsername().equals(username)) {
            throw new BankRestException(HttpStatus.BAD_REQUEST, "Не ваша карта!");
        }
        return card.getBalance();
    }

}

