package com.hwigle.budge.application.port.out;

import com.hwigle.budge.domain.Transaction;

public interface SaveTransactionPort {
    void save(Transaction transaction);
}
