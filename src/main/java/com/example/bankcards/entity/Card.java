package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.CardStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    @SequenceGenerator(name = "user_id_generator", sequenceName = "card_id_sequence", allocationSize = 1)
    private Long id;

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
