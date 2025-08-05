package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.error.ErrorResponseDto;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "User Cards", description = "Операции с картами текущего пользователя")
@RestController
@RequestMapping("/user/cards")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequiredArgsConstructor
public class UserCardController {

    private final CardService cardService;

    @Operation(
            summary = "Получить список карт",
            description = "Возвращает постраничный список карт текущего пользователя с фильтром по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список карт успешно получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CardDto> myCards(
            @RequestParam(defaultValue = "") String numberFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        String username = getCurrentUsername();
        Pageable pageable = PageRequest.of(page, size);
        return cardService.listMyCards(username, numberFilter, pageable);
    }

    @Operation(
            summary = "Запросить блокировку карты",
            description = "Создает запрос на блокировку карты с указанным ID текущего пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос на блокировку создан"),
            @ApiResponse(responseCode = "400", description = "Неверный формат ID карты",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этой операции",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @PostMapping("/{id}/block-request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestBlock(@PathVariable Long id) {
        cardService.requestBlock(getCurrentUsername(), id);
    }

    @Operation(
            summary = "Получить баланс карты",
            description = "Возвращает текущий баланс по карте с указанным ID текущего пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Баланс успешно получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к балансу карты",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Карта не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при получении баланса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return cardService.getBalance(getCurrentUsername(), id);
    }

    /**
     * Извлекает имя текущего аутентифицированного пользователя из контекста безопасности.
     *
     * @return имя пользователя, прошедшего аутентификацию
     */
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}
