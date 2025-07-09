package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.ChargeHistoryRequest;
import io.hhplus.tdd.controller.dto.ChargePointRequest;
import io.hhplus.tdd.controller.dto.ChargePointResponse;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.PointHistoryPort;
import io.hhplus.tdd.repository.PointPort;

public class PointService {
    private final PointPort pointPort;
    private final PointHistoryPort pointHistoryPort;

    public PointService(PointPort pointPort, PointHistoryPort pointHistoryPort) {
        this.pointPort = pointPort;
        this.pointHistoryPort = pointHistoryPort;
    }

    public ChargePointResponse charge(ChargePointRequest request) {
        // 유저 포인트 조회
        UserPoint userPoint = pointPort.getUserPoint(request.userId());

        // 포인트 충전
        UserPoint updated = userPoint.charge(request.amount());

        // 유저 포인트 저장
        UserPoint saved = pointPort.save(updated);

        // 유저 포인트 기록 저장
        ChargeHistoryRequest historyRequest = new ChargeHistoryRequest(request.userId(), request.amount(), TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryPort.save(historyRequest);

        // 응답 객체 반환
        return new ChargePointResponse(saved.id(), saved.point(), saved.updateMillis());
    }
}
