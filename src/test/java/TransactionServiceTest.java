import com.hwigle.budge.application.service.TransactionService;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TransactionServiceTest {

    @Test
    void 장부_기록_테스트() {
        // 1. 준비(Given) : 테스트에 필요한 재료 준비
        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                TransactionType.EXPENDITURE, // 1. 타입
                LocalDateTime.now(),         // 2. 시간
                "야구장 치킨",                // 3. 내용 (이게 빠졌나 확인!)
                new Money(25000L),           // 4. 돈 객체
                "식비"                       // 5. 카테고리
        );

        // 2. 실행(When) : 실제로 기록 기능 실행
        service.record(transaction);

        // 3. 검증(Then) : 지금은 콘솔로 확인, 나중에는 진짜 DB에 저장됐는지 확인
    }
}
