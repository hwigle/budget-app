package com.hwigle.budge.application.port.in;

import com.hwigle.budge.domain.Transaction;

import java.util.List;

public interface GetTransactionListUseCase {

    List<Transaction> getList();
}
