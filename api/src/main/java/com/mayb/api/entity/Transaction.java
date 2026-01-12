package com.mayb.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private BigDecimal amount; //dinheiro

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String category;

    private LocalDate date;

    // A transação pertence a uma família (para isolar os dados)
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    // A transação foi feita por um utilizador específico
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
