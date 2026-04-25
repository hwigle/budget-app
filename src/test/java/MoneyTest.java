import com.hwigle.budge.domain.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoneyTest {
    @Test
    void 돈_더하기_테스트() {
        Money m1 = new Money(1000L);
        Money m2 = new Money(500L);

        Money result = m1.plus(m2);

        assertThat(result.getAmount()).isEqualTo(1500L);

    }
}
