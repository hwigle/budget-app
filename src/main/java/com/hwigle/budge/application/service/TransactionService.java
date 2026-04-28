package com.hwigle.budge.application.service;

import com.hwigle.budge.application.port.in.RecordTransactionUseCase;
import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements RecordTransactionUseCase {

    // 난 저장소 구멍이 필요해라고 선언
    private final SaveTransactionPort saveTransactionPort;

    // 생성자로 주입받아 (나중에 스프링이 꽂아줄 거야)
    public TransactionService(SaveTransactionPort saveTransactionPort) {
        this.saveTransactionPort = saveTransactionPort;
    }

    @Override
    public void record(Transaction transaction) {
        // 장부를 기록할 때 지켜야 할 규칙
        System.out.println("장부에 기록 완료: " + transaction.getDescription());

        saveTransactionPort.save(transaction);
        System.out.println("서비스: 저장소 포트로 데이터를 보냈습니다.");
    }
}
