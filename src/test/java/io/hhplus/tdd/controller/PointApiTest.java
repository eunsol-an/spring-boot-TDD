package io.hhplus.tdd.controller;

import io.hhplus.tdd.controller.dto.GetPointHistoryResponse;
import io.hhplus.tdd.service.PointSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PointApi 테스트")
public class PointApiTest extends ApiTest {

    @Nested
    @DisplayName("포인트 충전 시")
    class ChargePoint {
        @Test
        @DisplayName("정상적으로 포인트가 충전된다")
        void 포인트충전() {
            final var request = PointSteps.포인트충전요청_생성(PointSteps.랜덤유저아이디_생성(), 1000L);

            final var response = PointSteps.포인트충전요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getLong("point")).isEqualTo(1000L);
        }

        @Test
        @DisplayName("최대 잔고 금액 초과 충전시 예외가 발생한다")
        void 포인트충전_최대잔고초과_예외() {
            final var request = PointSteps.포인트충전요청_생성(PointSteps.랜덤유저아이디_생성(), 100001L);

            final var response = PointSteps.포인트충전요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Nested
    @DisplayName("포인트 사용 시")
    class UsePoint {
        @Test
        @DisplayName("정상적으로 포인트가 차감된다")
        void 포인트사용() {
            long userId = PointSteps.랜덤유저아이디_생성();
            PointSteps.포인트충전요청(PointSteps.포인트충전요청_생성(userId, 1000L));
            final var request = PointSteps.포인트사용요쳥_생성(userId, 500L);

            final var response = PointSteps.포인트사용요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getLong("point")).isEqualTo(500L);
        }

        @Test
        @DisplayName("잔액 부족시 예외가 발생한다")
        void 포인트사용_잔고부족_예외() {
            long userId = PointSteps.랜덤유저아이디_생성();
            PointSteps.포인트충전요청(PointSteps.포인트충전요청_생성(userId, 500L));
            final var request = PointSteps.포인트사용요쳥_생성(userId, 1000L);

            final var response = PointSteps.포인트사용요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Nested
    @DisplayName("포인트 조회 시")
    class GetPoint {
        @Test
        @DisplayName("포인트 충전 후, 포인트가 정상적으로 조회된다")
        void 포인트조회() {
            long userId = PointSteps.랜덤유저아이디_생성();
            PointSteps.포인트충전요청(PointSteps.포인트충전요청_생성(userId, 1000L));

            final var response = PointSteps.포인트조회요청(userId);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getLong("point")).isEqualTo(1000L);
        }
    }

    @Nested
    @DisplayName("포인트 충전/사용 내역 조회 시")
    class GetPointHistory {
        @Test
        @DisplayName("포인트 충전과 사용 후, 히스토리 2건이 정상적으로 조회된다")
        void 포인트내역조회() {
            long userId = PointSteps.랜덤유저아이디_생성();
            PointSteps.포인트충전요청(PointSteps.포인트충전요청_생성(userId, 1000L));
            PointSteps.포인트사용요청(PointSteps.포인트사용요쳥_생성(userId, 500L));

            final var response = PointSteps.포인트내역조회요청(userId);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getList("pointHistoryList", GetPointHistoryResponse.class)).hasSize(2);
        }
    }

}
