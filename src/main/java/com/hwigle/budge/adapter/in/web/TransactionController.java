package com.hwigle.budge.adapter.in.web;

import com.hwigle.budge.application.port.in.GetTransactionListUseCase;
import com.hwigle.budge.application.port.in.RecordTransactionUseCase;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor // 아래에 있는 recordTransactionUseCase를 자동으로 연결
public class TransactionController {

    // '장부 기록하기' 인터페이스
    private final RecordTransactionUseCase recordTransactionUseCase;
    private final GetTransactionListUseCase getTransactionListUseCase;

    @GetMapping("/record")
    public String recordTransaction(
            @RequestParam String desc,
            @RequestParam Long amount,
            @RequestParam String category
    ) {
        // 1. 도메인 객체
        Transaction transaction = new Transaction(
                TransactionType.EXPENDITURE, // "이건 돈 쓴 거야!" (지출로 고정)
                LocalDateTime.now(),         // "지금 바로 쓴 거야!" (현재 시간)
                desc,                        // "어디에 썼냐면..." (설명)
                new Money(amount),           // "얼마냐면..." (돈 객체로 변신!)
                category                     // "분류는..." (카테고리)
        );

        // 2. 서비스 호출
        recordTransactionUseCase.record(transaction);

        // 3. 응답
        return "가계부 기록 성공! 내역 : " + desc + ", 금액 : " + amount + "원";
    }

    @GetMapping("/list")
    public List<Transaction> getAllTransaction() {
       return getTransactionListUseCase.getList() ;
    }
}
