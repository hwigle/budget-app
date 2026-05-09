package com.hwigle.budge.application.service;

import com.hwigle.budge.application.port.in.*;
import com.hwigle.budge.application.port.out.DeleteTransactionPort;
import com.hwigle.budge.application.port.out.LoadTransactionPort;
import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.application.port.out.UpdateTransactionPort;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor    // 필드에 final 붙은 놈들 모아서 생성자 자동 생성
public class TransactionService implements
        RecordTransactionUseCase,
        GetTransactionListUseCase,
        GetTransactionSummaryUseCase,
        DeleteTransactionUseCase,
        UpdateTransactionUseCase
{

    // 데이터 불러올 포트
    private final SaveTransactionPort saveTransactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final DeleteTransactionPort deleteTransactionPort;
    private final UpdateTransactionPort updateTransactionPort;


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
    public List<Transaction> getListByCategory(String category) {
        return loadTransactionPort.loadByCategory(category);
    }

    @Override
    public long getTotalExpenditure() {
        // 1. DB에서 모든 내역 가져오기
        List<Transaction> transactions = loadTransactionPort.loadAll();

        // 2. 지출만 골라서 금액 합치기
        Money total = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENDITURE)) // 1) 지출만 필터링
                .map(Transaction::getMoney)                                  // 2) Transaction에서 Money 객체 추출 (Stream<Money>)
                .reduce(Money.zero(), Money::plus);                           // 3) 0원부터 시작해서 Money의 add 메서드로 합산

        return total.getAmount(); // 최종 숫자만 반환                                                       // 합산
    }

    @Override
    public void deleteTransaction(Long id) {
        deleteTransactionPort.deleteById(id);
    }

    @Override
    @Transactional
    public void updateTransaction(Long id, Long amount, String description, String category) {
        // 기존 내역 불러오기
        Transaction transaction = loadTransactionPort.loadById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 내역을 찾을 수 없습니다."));

        // 도메인 객체 업데이트
        transaction.update(amount, description, category);

        // 저장소에 반영
        updateTransactionPort.update(transaction);
    }
}
