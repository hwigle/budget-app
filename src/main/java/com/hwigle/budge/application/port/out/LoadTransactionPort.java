package com.hwigle.budge.application.port.out;

import com.hwigle.budge.domain.Transaction;

import java.util.List;
import java.util.Optional;

public interface LoadTransactionPort {

    List<Transaction> loadAll();

    // 카테고리별 조회
    List<Transaction> loadByCategory(String category);

    // 상세조회
    Optional<Transaction> loadById(Long id);
}
