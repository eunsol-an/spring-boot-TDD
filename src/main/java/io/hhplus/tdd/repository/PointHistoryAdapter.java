package io.hhplus.tdd.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.controller.dto.ChargeHistoryRequest;
import io.hhplus.tdd.point.PointHistory;

public class PointHistoryAdapter implements PointHistoryPort {
    private final PointHistoryTable pointHistoryRepository;

    public PointHistoryAdapter(PointHistoryTable pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public PointHistory save(ChargeHistoryRequest request) {
        return pointHistoryRepository.insert(request.userId(), request.amount(), request.transactionType(), System.currentTimeMillis());
    }
}
