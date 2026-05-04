package com.hwigle.budge.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionJpaEntity, Long> {
    List<TransactionJpaEntity> findByCategory(String category);
}
