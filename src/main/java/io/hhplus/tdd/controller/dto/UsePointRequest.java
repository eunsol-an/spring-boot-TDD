package io.hhplus.tdd.controller.dto;

import org.springframework.util.Assert;

public record UsePointRequest(long userId, long amount) {
    public UsePointRequest {
        Assert.isTrue(amount > 0, "포인트 사용은 0보다 큰 금액만 가능합니다.");
    }
}
