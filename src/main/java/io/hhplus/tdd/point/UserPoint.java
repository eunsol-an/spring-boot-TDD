package io.hhplus.tdd.point;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    private static final long MAX_BALANCE = 100_000;

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public UserPoint charge(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("포인트 충전은 0보다 큰 금액만 가능합니다");
        }

        long newBalance = point + amount;
        if (newBalance > MAX_BALANCE) {
            throw new IllegalArgumentException("포인트 잔고는 100,000원을 초과할 수 없습니다");
        }

        return new UserPoint(id, point + amount, System.currentTimeMillis());
    }

    public UserPoint use(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("포인트 사용은 0보다 큰 금액만 가능합니다.");
        }
        if (point < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        return new UserPoint(id, point - amount, System.currentTimeMillis());
    }
}
