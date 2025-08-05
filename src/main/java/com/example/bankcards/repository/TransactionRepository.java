package com.example.bankcards.repository;

import com.example.bankcards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromCardOwnerIdOrderByCreatedAtDesc(Long ownerId);

    List<Transaction> findByToCardOwnerIdOrderByCreatedAtDesc(Long ownerId);

    Optional<Transaction> findByTxUuid(UUID txUuid);
}

