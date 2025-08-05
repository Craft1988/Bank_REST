package com.example.bankcards.controller;

import com.example.bankcards.dto.transaction.TransactionResponseDto;
import com.example.bankcards.dto.transaction.TransferRequest;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/transfers")
public class TransactionController {
    private final TransactionService txService;

    @PostMapping()
    public TransactionResponseDto doTransfer(
            @RequestBody TransferRequest req,
            @RequestParam Long userId
    ) {
        return txService.transfer(req, userId);
    }

    @GetMapping("/history/{userId}")
    public List<TransactionResponseDto> history(@PathVariable Long userId) {
        return txService.getHistory(userId);
    }
}
