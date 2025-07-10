package io.hhplus.tdd.controller.dto;

import org.springframework.util.Assert;

public record ChargePointRequest(long userId, long amount) {
    public ChargePointRequest {
        Assert.isTrue(amount > 0, "포인트 충전은 0보다 큰 금액만 가능합니다.");
    }
}
