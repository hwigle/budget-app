package com.hwigle.budge.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {
    private final Long id;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String description;
    private final Money money;
    private final String category;

    public Transaction(Long id, TransactionType type, LocalDateTime timestamp, String description, Money money, String category) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.money = money;
        this.category = category;
    }

    public Transaction(TransactionType type, LocalDateTime timestamp, String description, Money money, String category) {
        this(null, type, timestamp, description, money, category);
    }
}
