package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.entity.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Сервис для управления банковскими картами.
 */
public interface CardService {

    /**
     * Создает новую карту на основе переданных данных.
     *
     * @param req запрос на создание карты, содержащий необходимые данные
     * @return объект с информацией о созданной карте
     */
    CardDto createCard(CreateCardRequest req);

    /**
     * Обновляет статус существующей карты.
     *
     * @param cardId идентификатор карты
     * @param status новый статус карты
     * @return обновлённая информация о карте
     */
    CardDto updateStatus(Long cardId, CardStatus status);

    /**
     * Удаляет карту по её идентификатору.
     *
     * @param cardId идентификатор удаляемой карты
     */
    void deleteCard(Long cardId);

    /**
     * Возвращает список карт пользователя с возможностью фильтрации и пагинации.
     * Доступно только для роли USER.
     *
     * @param username имя пользователя
     * @param numberFilter фильтр по номеру карты
     * @param pageable параметры пагинации
     * @return страница с результатами
     */
    Page<CardDto> listMyCards(String username, String numberFilter, Pageable pageable);

    /**
     * Возвращает список всех карт с пагинацией.
     * Доступно только для роли ADMIN.
     *
     * @param pageable параметры пагинации
     * @return страница со всеми картами
     */
    Page<CardDto> listAll(Pageable pageable);

    /**
     * Отправляет запрос на блокировку карты.
     *
     * @param username имя пользователя, отправившего запрос
     * @param cardId идентификатор карты для блокировки
     */
    void requestBlock(String username, Long cardId);

    /**
     * Возвращает баланс по карте.
     *
     * @param username имя пользователя, владеющего картой
     * @param cardId идентификатор карты
     * @return текущий баланс карты
     */
    BigDecimal getBalance(String username, Long cardId);
}

