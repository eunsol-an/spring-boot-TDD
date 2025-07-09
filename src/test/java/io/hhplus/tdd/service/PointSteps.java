package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.ChargePointRequest;

public class PointSteps {
    public static ChargePointRequest 포인트충전요청_생성(long userId, long amount) {
        return new ChargePointRequest(userId, amount);
    }
}