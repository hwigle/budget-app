import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.application.service.TransactionService;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import com.hwigle.budge.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionServiceTest {

    @Test
    void 더하기_테스트() {
        Money tenWon = new Money(10);
        Money twentyWon = new Money(20);

        Money result = tenWon.plus(twentyWon);

        assertEquals(30, result.getAmount());
    }

    @Test
    void 음수_입력_시_에러_발생() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(-100);
        });
    }
}
