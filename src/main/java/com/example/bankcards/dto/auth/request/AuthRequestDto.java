package com.example.bankcards.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AuthRequestDto", description = "Данные для регистрации или входа пользователя")
public class AuthRequestDto {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 4, max = 20, message = "Имя пользователя должно быть от 4 до 20 символов")
    @Schema(description = "Имя пользователя", example = "ivan_petrov")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Пароль должен содержать не менее 8 символов, включая заглавные и строчные буквы, цифру и специальный символ"
    )
    @Schema(description = "Пароль пользователя", example = "SecureP@ss123")
    private String password;
}
