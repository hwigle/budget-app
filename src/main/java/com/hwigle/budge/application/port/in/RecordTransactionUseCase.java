package com.hwigle.budge.application.port.in;

import com.hwigle.budge.domain.Transaction;

public interface RecordTransactionUseCase {
    void record(Transaction transaction); // "장부를 기록하라"는 명령!
}
