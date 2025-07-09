package io.hhplus.tdd.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;

public class PointAdapter implements PointPort {
    private final UserPointTable pointRepository;

    public PointAdapter(UserPointTable pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public UserPoint getUserPoint(long userId) {
        return pointRepository.selectById(userId);
    }

    @Override
    public UserPoint save(UserPoint userPoint) {
        return pointRepository.insertOrUpdate(userPoint.id(), userPoint.point());
    }
}
