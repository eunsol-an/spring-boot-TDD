package io.hhplus.tdd.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserPoint 도메인 테스트")
class UserPointTest {

    @Nested
    @DisplayName("포인트 충전 시")
    class ChargePoint {
        @ParameterizedTest(name = "충전 금액이 {0}원 이상이면 정상적으로 충전된다")
        @ValueSource(longs = {1, 500, 999})
        @DisplayName("정상적으로 포인트가 충전된다")
        void 포인트_충전(long validAmount) {
            // given
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            // when
            UserPoint charged = userPoint.charge(validAmount);

            // then
            assertEquals(1000L + validAmount, charged.point());
        }

        @ParameterizedTest(name = "{0}원을 충전하면 예외가 발생한다")
        @ValueSource(longs = {0, -100})
        @DisplayName("0원 이하 금액으로 충전시 예외가 발생한다")
        void 충전금액_경계값_예외(long invalidAmount) {
            // given
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            // expect
            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.charge(invalidAmount);
            });
        }

        @ParameterizedTest(name = "기존 포인트 {0}원 + 충전 {1}원 이면 예외가 발생한다")
        @CsvSource({
                "99999,2",
                "100000,1",
                "95000,6000"
        })
        @DisplayName("최대 잔고 금액 초과 충전시 예외가 발생한다")
        void 최대잔고_초과_예외(long currentPoint, long chargeAmount) {
            UserPoint userPoint = new UserPoint(1L, currentPoint, System.currentTimeMillis());

            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.charge(chargeAmount);
            });
        }

    }


    @Nested
    @DisplayName("포인트 사용 시")
    class UsePoint {
        @ParameterizedTest(name = "사용 금액이 {0}원 이상이면 정상적으로 차감된다")
        @ValueSource(longs = {1, 500, 1000})
        @DisplayName("정상적으로 포인트가 차감된다")
        void 포인트_사용(long validAmount) {
            UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

            UserPoint used = userPoint.use(validAmount);

            assertEquals(1000L - validAmount, used.point());
        }

        @ParameterizedTest(name = "보유 금액 {0}원, 사용 금액 {1}원 이면 예외가 발생한다")
        @CsvSource({
                "1000,1001",
                "500,1000",
                "0,1"
        })
        @DisplayName("잔액보다 큰 금액을 사용하면 예외가 발생한다")
        void 포인트_부족_예외(long currentPoint, long useAmount) {
            UserPoint userPoint = new UserPoint(1L, currentPoint, System.currentTimeMillis());

            assertThrows(IllegalArgumentException.class, () -> {
                userPoint.use(useAmount);
            });
        }
    }
}