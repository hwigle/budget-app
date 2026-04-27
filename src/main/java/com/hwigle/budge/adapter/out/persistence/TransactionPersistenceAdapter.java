package com.hwigle.budge.adapter.out.persistence;

import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // "스프링아, 내가 진짜 DB 부품이니까 나중에 서비스에 나를 꽂아줘!"라는 뜻
@RequiredArgsConstructor // final이 붙은 리포지토리를 스프링이 자동으로 주입해줘 (생성자 대신!)
public class TransactionPersistenceAdapter implements SaveTransactionPort {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        // 1. 도메인(Transaction)을 DB용 엔티티(JpaEntity)로 변환
        TransactionJpaEntity jpaEntity = new TransactionJpaEntity(
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getDescription(),
                transaction.getMoney().getAmount(), // Money 객체에서 숫자만 가져오기
                transaction.getCategory()
        );

        // 2. 리포지토리를 사용해서 진짜 DB에 저장
        transactionRepository.save(jpaEntity);

        System.out.println("DB 어댑터 : 진짜 DB(H2)에 저장을 완료했습니다. -> " + transaction.getDescription());
    }
}
