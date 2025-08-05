package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CreateCardRequest;
import com.example.bankcards.dto.card.UpdateCardStatusRequest;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cards")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCardController {
    private final CardService cardService;

    @PostMapping
    public CardDto create(@RequestBody CreateCardRequest req) {
        return cardService.createCard(req);
    }

    @PatchMapping("/{id}/status")
    public CardDto updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateCardStatusRequest req
    ) {
        return cardService.updateStatus(id, req.getStatus());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @GetMapping
    public Page<CardDto> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.listAll(pageable);
    }
}
