package io.hhplus.tdd.controller.dto;

import io.hhplus.tdd.point.TransactionType;
import org.springframework.util.Assert;

public record ChargeHistoryRequest(long userId, long amount, TransactionType transactionType, long updateMillis) {
    public ChargeHistoryRequest {
        Assert.isTrue(amount > 0, "포인트 충전은 0보다 큰 금액만 가능합니다.");
        Assert.isTrue(TransactionType.CHARGE.isSameType(transactionType), "충전 타입이어야 합니다.");
    }
}
