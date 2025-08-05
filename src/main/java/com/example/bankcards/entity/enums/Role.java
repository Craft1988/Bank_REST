package com.example.bankcards.entity.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли пользователей в системе, используемые для авторизации.
 * Реализуют интерфейс {@link org.springframework.security.core.GrantedAuthority}.
 */
public enum Role implements GrantedAuthority {

    /**
     * Роль обычного пользователя. Имеет доступ к пользовательским функциям.
     */
    ROLE_USER,

    /**
     * Роль администратора. Имеет доступ к административным функциям и управлению системой.
     */
    ROLE_ADMIN;

    /**
     * Возвращает строковое представление полномочия (authority), требуемое Spring Security.
     *
     * @return имя роли как строка (например, "ROLE_USER", "ROLE_ADMIN")
     */
    @Override
    public String getAuthority() {
        return name();
    }
}

