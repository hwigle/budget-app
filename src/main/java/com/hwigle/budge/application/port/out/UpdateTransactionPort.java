package com.hwigle.budge.application.port.out;

import com.hwigle.budge.domain.Transaction;

public interface UpdateTransactionPort {
    void update(Transaction transaction);
}
