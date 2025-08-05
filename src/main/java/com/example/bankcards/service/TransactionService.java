package com.example.bankcards.service;

import com.example.bankcards.dto.transaction.TransactionResponseDto;
import com.example.bankcards.dto.transaction.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.enums.TransactionStatus;
import com.example.bankcards.exception.BankRestException;
import com.example.bankcards.mapper.TransactionMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final CardRepository cardRepo;
    private final TransactionRepository txRepo;
    private final TransactionMapper txMapper;

    public TransactionResponseDto transfer(TransferRequest transferRequest, Long userId) {
        Card from = cardRepo.findById(transferRequest.getFromCardId())
                .orElseThrow(() -> new EntityNotFoundException("Карта отправителя не найдена"));
        Card to   = cardRepo.findById(transferRequest.getToCardId())
                .orElseThrow(() -> new EntityNotFoundException("Карта получателя не найдена"));

        if (!from.getOwner().getId().equals(userId) ||
                !to.getOwner().getId().equals(userId)) {
            throw new BankRestException(HttpStatus.CONFLICT, "Нельзя переводить между чужими картами");
        }

        Transaction tx = new Transaction(from, to, transferRequest.getAmount());

        tx = txRepo.save(tx);

        try {
            validateTransfer(from, to, transferRequest.getAmount());

            from.setBalance(from.getBalance().subtract(transferRequest.getAmount()));
            to.setBalance(to.getBalance().add(transferRequest.getAmount()));

            cardRepo.save(from);
            cardRepo.save(to);

            tx.setStatus(TransactionStatus.COMPLETED);
            tx.setCompletedAt(LocalDateTime.now());
            txRepo.save(tx);

        } catch (RuntimeException ex) {
            tx.setStatus(TransactionStatus.FAILED);
            tx.setCompletedAt(LocalDateTime.now());
            txRepo.save(tx);
            throw new BankRestException(HttpStatus.INTERNAL_SERVER_ERROR, "Что то пошло не так!");
        }

        return txMapper.toDto(tx);
    }

    private void validateTransfer(Card from, Card to, BigDecimal amount) {
        if (from.getStatus() != CardStatus.ACTIVE ||
                to.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Обе карты должны быть активны");
        }
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }

    public List<TransactionResponseDto> getHistory(Long userId) {
        List<Transaction> sent = txRepo.findByFromCardOwnerIdOrderByCreatedAtDesc(userId);
        List<Transaction> recv = txRepo.findByToCardOwnerIdOrderByCreatedAtDesc(userId);

        return Stream.concat(sent.stream(), recv.stream())
                .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                .map(txMapper::toDto)
                .toList();
    }
}

