package com.hwigle.budge.application.port.in;

public interface UpdateTransactionUseCase {
    void updateTransaction(Long id, Long amount, String description, String category);
}
