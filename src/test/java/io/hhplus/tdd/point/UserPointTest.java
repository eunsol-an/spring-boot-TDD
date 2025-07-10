package io.hhplus.tdd.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserPoint 도메인 테스트")
class UserPointTest {

    @Nested
    @DisplayName("포인트 충전 시")
    class ChargePoint {
        @Test
        @DisplayName("정상적으로 포인트가 충전된다")
        void 포인트_충전() {
            // given
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            // when
            UserPoint charged = userPoint.charge(500L);

            // then
            assertEquals(1500L, charged.point());
        }

        @Test
        @DisplayName("음수 금액으로 충전시 예외가 발생한다")
        void 충전금액_음수_예외() {
            // given
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            // expect
            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.charge(-100L);
            });
        }

        @Test
        @DisplayName("최대 잔고 금액 초과 충전시 예외가 발생한다")
        void 최대잔고_초과_예외() {
            UserPoint userPoint = new UserPoint(1L, 90000L, System.currentTimeMillis());

            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.charge(20000L);
            });
        }

    }


    @Nested
    @DisplayName("포인트 사용 시")
    class UsePoint {
        @Test
        @DisplayName("정상적으로 포인트가 차감된다")
        void 포인트_사용() {
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            UserPoint used = userPoint.use(300L);

            assertEquals(700L, used.point());
        }

        @Test
        @DisplayName("잔액 부족시 예외가 발생한다")
        void 포인트_부족_예외() {
            UserPoint userPoint = new UserPoint(1L, 500L, System.currentTimeMillis());

            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.use(1000L);
            });
        }
    }
}