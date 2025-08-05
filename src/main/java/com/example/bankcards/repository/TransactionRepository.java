package com.example.bankcards.repository;

import com.example.bankcards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//TODO javadoc
/**
 * Репозиторий для доступа к транзакциям.
 * Предоставляет методы для поиска транзакций по владельцу карты и по UUID.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Находит список транзакций, инициированных пользователем (как отправителем),
     * отсортированных по дате создания в порядке убывания.
     *
     * @param ownerId идентификатор владельца карты-отправителя
     * @return список транзакций, где указанный пользователь является отправителем
     */
    List<Transaction> findByFromCardOwnerIdOrderByCreatedAtDesc(Long ownerId);

    /**
     * Находит список транзакций, полученных пользователем (как получателем),
     * отсортированных по дате создания в порядке убывания.
     *
     * @param ownerId идентификатор владельца карты-получателя
     * @return список транзакций, где указанный пользователь является получателем
     */
    List<Transaction> findByToCardOwnerIdOrderByCreatedAtDesc(Long ownerId);

    /**
     * Ищет транзакцию по уникальному идентификатору (UUID).
     *
     * @param txUuid уникальный идентификатор транзакции
     * @return {@link Optional}, содержащий транзакцию, если она найдена
     */
    Optional<Transaction> findByTxUuid(UUID txUuid);
}


