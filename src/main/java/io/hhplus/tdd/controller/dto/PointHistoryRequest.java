package io.hhplus.tdd.controller.dto;

import io.hhplus.tdd.point.TransactionType;
import org.springframework.util.Assert;

public record PointHistoryRequest(long userId, long amount, TransactionType transactionType, long updateMillis) {
    public PointHistoryRequest {
        Assert.isTrue(amount > 0, "포인트 충전/사용은 0보다 큰 금액만 가능합니다.");
    }
}
