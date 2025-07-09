package io.hhplus.tdd.repository;

import io.hhplus.tdd.controller.dto.ChargeHistoryRequest;
import io.hhplus.tdd.point.PointHistory;

public interface PointHistoryPort {
    PointHistory save(final ChargeHistoryRequest request);
}
