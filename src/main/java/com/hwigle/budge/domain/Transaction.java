package com.hwigle.budge.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {
    private final Long id;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private String description;
    private Money money;
    private String category;

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

    public void update(Long amount, String description, String category) {
        this.money = new Money(amount);
        this.description = description;
        this.category = category;
    }
}
