package com.hwigle.budge.application.service;

import com.hwigle.budge.application.port.in.GetTransactionListUseCase;
import com.hwigle.budge.application.port.in.RecordTransactionUseCase;
import com.hwigle.budge.application.port.out.LoadTransactionPort;
import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements RecordTransactionUseCase, GetTransactionListUseCase {

    // 데이터 불러올 포트
    private final SaveTransactionPort saveTransactionPort;
    private final LoadTransactionPort loadTransactionPort;


    // 생성자로 주입받아 (나중에 스프링이 꽂아줄 거야)
    public TransactionService(SaveTransactionPort saveTransactionPort, LoadTransactionPort loadTransactionPort) {
        this.saveTransactionPort = saveTransactionPort;
        this.loadTransactionPort = loadTransactionPort;
    }

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
}
