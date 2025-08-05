package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user/cards")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequiredArgsConstructor
public class UserCardController {
    private final CardService cardService;

    @GetMapping
    public Page<CardDto> myCards(
            @RequestParam(defaultValue = "") String numberFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        String username = getCurrentUsername();
        Pageable pageable = PageRequest.of(page, size);
        return cardService.listMyCards(username, numberFilter, pageable);
    }

    @PostMapping("/{id}/block-request")
    public void requestBlock(@PathVariable Long id) {
        cardService.requestBlock(getCurrentUsername(), id);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return cardService.getBalance(getCurrentUsername(), id);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }
}

