package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link User}.
 * Предоставляет методы доступа к данным пользователей.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Ищет пользователя по имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return {@link Optional}, содержащий найденного пользователя, если он существует
     */
    Optional<User> findUserByUsername(String username);
}

