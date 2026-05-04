package com.hwigle.budge.application.service;

import com.hwigle.budge.application.port.in.DeleteTransactionUseCase;
import com.hwigle.budge.application.port.in.GetTransactionListUseCase;
import com.hwigle.budge.application.port.in.GetTransactionSummaryUseCase;
import com.hwigle.budge.application.port.in.RecordTransactionUseCase;
import com.hwigle.budge.application.port.out.DeleteTransactionPort;
import com.hwigle.budge.application.port.out.LoadTransactionPort;
import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor    // 필드에 final 붙은 놈들 모아서 생성자 자동 생성
public class TransactionService implements
        RecordTransactionUseCase,
        GetTransactionListUseCase,
        GetTransactionSummaryUseCase,
        DeleteTransactionUseCase
{

    // 데이터 불러올 포트
    private final SaveTransactionPort saveTransactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final DeleteTransactionPort deleteTransactionPort;

    // 저장 로직
    @Override
    public void record(Transaction transaction) {
        // 장부를 기록할 때 지켜야 할 규칙
        System.out.println("장부에 기록 완료: " + transaction.getDescription());

        saveTransactionPort.save(transaction);
        System.out.println("서비스: 저장소 포트로 데이터를 보냈습니다.");
    }

    // 조회 로직
    @Override
    public List<Transaction> getList() {
        System.out.println("서비스 : 저장소에서 전체 내역을 불러옵니다.");
        return loadTransactionPort.loadAll();
    }

    @Override
    public long getTotalExpenditure() {
        // 1. DB에서 모든 내역 가져오기
        List<Transaction> transactions = loadTransactionPort.loadAll();

        // 2. 지출만 골라서 금액 합치기
        return transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENDITURE)) // 지출 필터
                .mapToLong(t -> t.getMoney().getAmount())           // 금액으로 변환
                .sum();                                                         // 합산
    }

    @Override
    public void deleteTransaction(Long id) {
        deleteTransactionPort.deleteById(id);
    }
}
