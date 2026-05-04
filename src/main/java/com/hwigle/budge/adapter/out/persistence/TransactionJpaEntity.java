package com.hwigle.budge.adapter.out.persistence;

import com.hwigle.budge.domain.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity                         // "이 클래스는 DB 테이블이랑 연결될 녀석이야!"라고 선언
@Table(name = "transaction")    // 실제 DB에 생성될 테이블 이름을 지정
@Getter                         // 필드들에 대해 getAmount(), getDescription() 같은 메서드를 자동 생성
@NoArgsConstructor              // JPA가 내부적으로 객체를 만들 때 필요한 '기본 생성자'를 자동 생성
public class TransactionJpaEntity {
    @Id // 이 필드가 테이블의 '기본키(PK)'임을 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호를 우리가 안 매기고 DB가 자동으로 1, 2, 3... 늘려가며 붙이게 함
    private Long id; // DB에서 자동으로 붙여주는 번호

    @Enumerated(EnumType.STRING) // Enum(INCOME, EXPENDITURE)을 DB에 저장할 때 숫자가 아닌 '문자열' 그대로 저장함
    private TransactionType type;

    private LocalDateTime timestamp;
    private String description;
    private Long amount; // Money 객체 대신 일단 숫자로 저장하자!
    private String category;

    // [조회용] 도메인(Business 로직용) 모델을 받아서 엔티티(DB 저장용)로 변환해주는 생성자
    public TransactionJpaEntity(Long id, TransactionType type, LocalDateTime timestamp, String description, Long amount, String category) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    // [저장용] 컨트롤러에서 처음 만들 때 사용 (ID를 몰라도 됨)
    public TransactionJpaEntity(TransactionType type, LocalDateTime timestamp, String description, Long amount, String category) {
        this.id = null; // 저장용이니까 ID는 null
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
