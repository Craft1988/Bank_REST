package com.example.bankcards.dto.user.response;

import com.example.bankcards.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserDto", description = "Данные зарегистрированного пользователя")
public class UserDto {

    @Schema(description = "Имя пользователя", example = "ivan_petrov")
    private String username;

    @Schema(description = "Роль пользователя в системе", example = "ROLE_ADMIN")
    private Role role;
}

