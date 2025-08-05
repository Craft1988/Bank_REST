package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.util.cardutils.CardMaskConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_id_generator")
    @SequenceGenerator(name = "card_id_generator", sequenceName = "card_id_sequence", allocationSize = 1)
    private Long id;

    @Convert(converter = CardMaskConverter.class)
    private String number;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private BigDecimal balance = BigDecimal.ZERO;
}
