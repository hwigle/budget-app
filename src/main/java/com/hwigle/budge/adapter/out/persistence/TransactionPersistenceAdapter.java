package com.hwigle.budge.adapter.out.persistence;

import com.hwigle.budge.application.port.out.DeleteTransactionPort;
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
public class TransactionPersistenceAdapter implements
        SaveTransactionPort,
        LoadTransactionPort,
        DeleteTransactionPort
{

    private final TransactionRepository repository;

    // 어댑터 내부의 헬퍼 메서드
    private Transaction mapToDomain(TransactionJpaEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getType(),
                entity.getTimestamp(),
                entity.getDescription(),
                new Money(entity.getAmount()),
                entity.getCategory()
        );
    }

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
        return repository.findAll().stream()
                .map(this::mapToDomain) // "위에서 만든 mapToDomain 메서드를 써라!"
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
