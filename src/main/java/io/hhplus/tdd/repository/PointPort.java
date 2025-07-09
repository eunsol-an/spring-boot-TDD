package io.hhplus.tdd.repository;

import io.hhplus.tdd.point.UserPoint;

public interface PointPort {
    UserPoint getUserPoint(long userId);
    UserPoint save(final UserPoint userPoint);
}
