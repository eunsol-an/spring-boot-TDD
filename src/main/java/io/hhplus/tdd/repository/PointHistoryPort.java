package io.hhplus.tdd.repository;

import io.hhplus.tdd.controller.dto.PointHistoryRequest;
import io.hhplus.tdd.point.PointHistory;

public interface PointHistoryPort {
    PointHistory save(final PointHistoryRequest request);
}
