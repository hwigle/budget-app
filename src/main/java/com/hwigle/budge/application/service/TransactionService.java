package com.hwigle.budge.application.service;

import com.hwigle.budge.application.port.in.RecordTransactionUseCase;
import com.hwigle.budge.domain.Transaction;

public class TransactionService implements RecordTransactionUseCase {

    @Override
    public void record(Transaction transaction) {
        // 장부를 기록할 때 지켜야 할 규칙
        System.out.println("장부에 기록 완료: " + transaction.getDescription());
    }
}
