package io.hhplus.tdd.repository;

import io.hhplus.tdd.controller.dto.PointHistoryRequest;
import io.hhplus.tdd.point.PointHistory;

import java.util.List;

public interface PointHistoryPort {
    PointHistory save(final PointHistoryRequest request);
    List<PointHistory> getPointHistoryList(long userId);
}
