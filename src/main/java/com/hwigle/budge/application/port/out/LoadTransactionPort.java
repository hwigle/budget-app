package com.hwigle.budge.application.port.out;

import com.hwigle.budge.domain.Transaction;

import java.util.List;

public interface LoadTransactionPort {

    List<Transaction> loadAll();
}
