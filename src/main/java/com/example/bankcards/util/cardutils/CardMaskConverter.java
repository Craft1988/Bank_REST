package com.example.bankcards.util.cardutils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CardMaskConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // при сохранении сохраняем без изменений
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() < 4) {
            return dbData;
        }
        String last4 = dbData.substring(dbData.length() - 4);
        return "**** **** **** " + last4;
    }
}

