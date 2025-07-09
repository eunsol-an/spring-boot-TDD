package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.ChargePointRequest;
import io.hhplus.tdd.controller.dto.UsePointRequest;

public class PointSteps {
    public static ChargePointRequest 포인트충전요청_생성(long userId, long amount) {
        return new ChargePointRequest(userId, amount);
    }

    public static UsePointRequest 포인트사용요쳥_생성(long userId, long amount) {
        return new UsePointRequest(userId, amount);
    }
}