package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Card}.
 * Предоставляет доступ к операциям чтения карт из базы данных.
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Возвращает страницу всех карт в системе.
     * Используется администратором для просмотра полного списка.
     *
     * @param pageable параметры пагинации
     * @return страница с картами
     */
    Page<Card> findAll(Pageable pageable);

    /**
     * Возвращает страницу карт, принадлежащих указанному пользователю,
     * фильтруя их по части номера карты (без учета регистра).
     *
     * @param username имя пользователя — владельца карт
     * @param numberPart часть номера карты для фильтрации
     * @param pageable параметры пагинации
     * @return страница с отфильтрованными картами пользователя
     */
    Page<Card> findByOwnerUsernameAndNumberContainingIgnoreCase(
            String username,
            String numberPart,
            Pageable pageable
    );
}

