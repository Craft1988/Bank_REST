package com.example.bankcards.controller;

import com.example.bankcards.dto.error.ErrorResponseDto;
import com.example.bankcards.dto.transaction.TransactionResponseDto;
import com.example.bankcards.dto.transaction.TransferRequest;
import com.example.bankcards.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User Transfers", description = "Операции перевода средств и история транзакций")
@RestController
@RequestMapping("/user/transfers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService txService;

    @Operation(
            summary = "Совершить перевод",
            description = "Осуществляет перевод средств от имени пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Перевод успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные перевода",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для совершения перевода",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponseDto doTransfer(
            @Valid  @RequestBody TransferRequest req,
            @RequestParam Long userId
    ) {
        return txService.transfer(req, userId);
    }

    @Operation(
            summary = "Получить историю переводов",
            description = "Возвращает список всех транзакций пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "История транзакций получена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Доступ к истории запрещён",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "История транзакций не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/history/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDto> history(@PathVariable Long userId) {
        return txService.getHistory(userId);
    }
}
