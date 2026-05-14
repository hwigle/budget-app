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
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    // '장부 기록하기' 인터페이스
    private final RecordTransactionUseCase recordTransactionUseCase;
    private final GetTransactionListUseCase getTransactionListUseCase;
    private final GetTransactionSummaryUseCase getTransactionSummaryUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final GetCategorySummaryUseCase getCategorySummaryUseCase;

    @PostMapping("/record")
    public String recordTransaction(@RequestBody TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.getType(),
                java.time.LocalDateTime.now(),
                request.getDescription(),
                new com.hwigle.budge.domain.Money(request.getAmount()), // 숫자를 Money로 감싸기
                request.getCategory()
        );

        // 2. 서비스 호출
        recordTransactionUseCase.record(transaction);

        // 3. 응답
        return "성공";
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

    @lombok.Getter
    @lombok.Setter
    @lombok.NoArgsConstructor
    static class TransactionRequest {
        private com.hwigle.budge.domain.TransactionType type;
        private String description;
        private Long amount;
        private String category;
    }
}
