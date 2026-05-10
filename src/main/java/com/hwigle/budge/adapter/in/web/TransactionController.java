package com.hwigle.budge.adapter.in.web;

import com.hwigle.budge.application.port.in.*;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor // 아래에 있는 recordTransactionUseCase를 자동으로 연결
public class TransactionController {

    // '장부 기록하기' 인터페이스
    private final RecordTransactionUseCase recordTransactionUseCase;
    private final GetTransactionListUseCase getTransactionListUseCase;
    private final GetTransactionSummaryUseCase getTransactionSummaryUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final GetCategorySummaryUseCase getCategorySummaryUseCase;

    @GetMapping("/record")
    public String recordTransaction(
            @RequestParam String desc,
            @RequestParam Long amount,
            @RequestParam String category,
            @RequestParam TransactionType type
    ) {
        // 1. 도메인 객체
        Transaction transaction = new Transaction(
                type,
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
    public List<Transaction> getAllTransaction(@RequestParam(required = false) String category) {
        if(category != null && !category.isEmpty()) {
            return getTransactionListUseCase.getListByCategory(category);
        }

        return getTransactionListUseCase.getList();
    }

    @GetMapping("/total")
    public String getTotal() {
        long total = getTransactionSummaryUseCase.getTotalExpenditure();
        return "현재까지 총 지출은 " + total + "원 입니다.";
    }

    // 삭제
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        deleteTransactionUseCase.deleteTransaction(id);
        return id + "번 내역이 삭제되었습니다.";
    }

    // 수정
    @GetMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            @RequestParam Long amount,
            @RequestParam String desc,
            @RequestParam String category
    ) {
        updateTransactionUseCase.updateTransaction(id, amount, desc, category);

        return id + "번 내역이 성공적으로 수정되었습니다: " + desc + " (" + amount + "원)";
    }

    // 통계
    @GetMapping("/summary")
    public Map<String, Long> getSummary() {
        return getCategorySummaryUseCase.getCategorySummary();
    }
}
