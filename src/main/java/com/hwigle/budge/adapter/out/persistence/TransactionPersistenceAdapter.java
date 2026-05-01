package com.hwigle.budge.adapter.out.persistence;

import com.hwigle.budge.application.port.out.LoadTransactionPort;
import com.hwigle.budge.application.port.out.SaveTransactionPort;
import com.hwigle.budge.domain.Money;
import com.hwigle.budge.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // "스프링아, 내가 진짜 DB 부품이니까 나중에 서비스에 나를 꽂아줘!"라는 뜻
@RequiredArgsConstructor // final이 붙은 리포지토리를 스프링이 자동으로 주입해줘 (생성자 대신!)
public class TransactionPersistenceAdapter implements SaveTransactionPort, LoadTransactionPort {

    private final TransactionRepository repository;

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
        repository.save(jpaEntity);

        System.out.println("DB 어댑터 : 진짜 DB(H2)에 저장을 완료했습니다. -> " + transaction.getDescription());
    }

    @Override
    public List<Transaction> loadAll() {
        // 리포지토리에서 모든 엔티티 가져오기
        List<TransactionJpaEntity> entities = repository.findAll();

        return entities.stream()                                    // 1. 박스(Entity)들을 컨베이어 벨트에 올린다.
                .map(entity -> new Transaction(     // 2. 박스를 까서 내용물(도메인)로 바꾼다.
                        entity.getType(),
                        entity.getTimestamp(),
                        entity.getDescription(),
                        new Money(entity.getAmount()),
                        entity.getCategory()
                ))
                .collect(Collectors.toList());                      // 3. 바뀐 내용물을 다시 새 바구니(List)에 담는다.
    }
}
