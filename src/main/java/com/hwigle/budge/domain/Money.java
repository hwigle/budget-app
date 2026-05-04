package com.hwigle.budge.domain;

import lombok.Getter;

@Getter
public class Money {
    private final long amount;

    public Money(long amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("금액은 0원보다 적을 수 없습니다.");
        }
        this.amount = amount;
    }

    // 더하기
    public Money plus(Money other) {
        return new Money(this.amount + other.amount);
    }

    // 빼기
    public Money minus(Money other) {

        return new Money(this.amount - other.amount);
    }

    // 0원 객체
    public static Money zero() {
        return new Money(0);
    }
}
