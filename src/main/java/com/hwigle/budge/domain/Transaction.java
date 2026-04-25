package com.hwigle.budge.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String description;
    private final Money money;
    private final String category;

    public Transaction(TransactionType type, LocalDateTime timestamp, String description, Money money, String category) {
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.money = money;
        this.category = category;
    }
}
