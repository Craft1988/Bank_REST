package com.example.bankcards.util.validator;

import com.example.bankcards.dto.transaction.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransferRequestValidator implements ConstraintValidator<ValidTransfer, TransferRequest> {
    @Override
    public boolean isValid(TransferRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;
        return request.getFromCardId() != null && request.getToCardId() != null &&
                !request.getFromCardId().equals(request.getToCardId());
    }
}