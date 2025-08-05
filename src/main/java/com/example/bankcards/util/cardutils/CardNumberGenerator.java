package com.example.bankcards.util.cardutils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardNumberGenerator {

    public static String number() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int block = 1000 + random.nextInt(9000);
            cardNumber.append(block);

            if (i < 3) {
                cardNumber.append(" ");
            }
        }

        return cardNumber.toString();
    }
}
