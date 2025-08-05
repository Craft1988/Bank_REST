package com.example.bankcards.entity.enums;

/**
 * Статусы, отражающие текущее состояние транзакции.
 */
public enum TransactionStatus {

    /**
     * Транзакция находится в ожидании обработки.
     */
    PENDING,

    /**
     * Транзакция была успешно выполнена.
     */
    COMPLETED,

    /**
     * Транзакция завершилась с ошибкой.
     */
    FAILED
}

