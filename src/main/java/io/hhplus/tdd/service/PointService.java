package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.*;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.PointHistoryPort;
import io.hhplus.tdd.repository.PointPort;

import java.util.List;

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
        PointHistoryRequest historyRequest = new PointHistoryRequest(request.userId(), request.amount(), TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryPort.save(historyRequest);

        // 응답 객체 반환
        return new ChargePointResponse(saved.id(), saved.point(), saved.updateMillis());
    }

    public UsePointResponse use(UsePointRequest request) {
        // 유저 포인트 조회
        UserPoint userPoint = pointPort.getUserPoint(request.userId());

        // 포인트 사용
        UserPoint updated = userPoint.use(request.amount());

        // 유저 포인트 저장
        UserPoint saved = pointPort.save(updated);

        // 유저 포인트 기록 저장
        PointHistoryRequest historyRequest = new PointHistoryRequest(request.userId(), request.amount(), TransactionType.USE, System.currentTimeMillis());
        pointHistoryPort.save(historyRequest);

        return new UsePointResponse(saved.id(), saved.point(), saved.updateMillis());
    }

    public GetPointResponse get(long userId) {
        final UserPoint userPoint = pointPort.getUserPoint(userId);
        return new GetPointResponse(userPoint.id(), userPoint.point(), userPoint.updateMillis());
    }

    public List<GetPointHistoryResponse> getHistoryList(long userId) {
        List<PointHistory> pointHistoryList = pointHistoryPort.getPointHistoryList(userId);
        return pointHistoryList.stream()
                .map(pointHistory -> new GetPointHistoryResponse(
                        pointHistory.id(),
                        pointHistory.userId(),
                        pointHistory.amount(),
                        pointHistory.type(),
                        pointHistory.updateMillis()
                ))
                .toList();
    }
}
