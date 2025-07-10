package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.*;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.PointAdapter;
import io.hhplus.tdd.repository.PointHistoryAdapter;
import io.hhplus.tdd.repository.PointHistoryPort;
import io.hhplus.tdd.repository.PointPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("PointService 테스트")

public class PointServiceTest {

    private PointService pointService;

    private PointPort pointPort;
    private UserPointTable pointRepository;

    private PointHistoryPort pointHistoryPort;
    private PointHistoryTable pointHistoryRepository;

    @BeforeEach
    void setUp() {
        pointRepository = new UserPointTable();
        pointPort = new PointAdapter(pointRepository);

        pointHistoryRepository = new PointHistoryTable();
        pointHistoryPort = new PointHistoryAdapter(pointHistoryRepository);

        pointService = new PointService(pointPort, pointHistoryPort);
    }

    @Nested
    @DisplayName("포인트 충전 시")
    class ChargePoint {
        @Test
        @DisplayName("정상적으로 포인트가 충전된다")
        void 포인트충전() {
            // given
            final ChargePointRequest request = PointSteps.포인트충전요청_생성(1L, 1000L);

            // when
            ChargePointResponse charged = pointService.charge(request);

            // then
            assertEquals(request.userId(), charged.userId());
            assertEquals(request.amount(), charged.point());
        }

        @Test
        @DisplayName("최대 잔고 금액 초과 충전시 예외가 발생한다")
        void 포인트충전_최대잔고초과_예외() {
            ChargePointResponse charged = pointService.charge(PointSteps.포인트충전요청_생성(1L, 90000L));
            assertThat(charged.point()).isEqualTo(90000L);

            assertThrows(IllegalArgumentException.class, () -> {
                pointService.charge(PointSteps.포인트충전요청_생성(charged.userId(), 20000L));;
            });
        }

        @Test
        @DisplayName("충전 내역이 히스토리에 저장된다")
        void 포인트충전_히스토리() {
            final ChargePointRequest request = PointSteps.포인트충전요청_생성(1L, 1000L);

            ChargePointResponse charged = pointService.charge(request);

            List<PointHistory> historyList = pointHistoryRepository.selectAllByUserId(charged.userId());
            assertEquals(1, historyList.size());
            assertEquals(request.amount(), historyList.get(0).amount());
            assertEquals(TransactionType.CHARGE, historyList.get(0).type());
        }
    }

    @Nested
    @DisplayName("포인트 사용 시")
    class UsePoint {
        @Test
        @DisplayName("정상적으로 포인트가 차감된다")
        void 포인트사용() {
            UserPoint initialUserPoint = pointRepository.insertOrUpdate(1L, 1000L);
            final UsePointRequest request = PointSteps.포인트사용요쳥_생성(1L, 500L);

            UsePointResponse used = pointService.use(request);

            assertEquals(request.userId(), used.userId());
            assertEquals(request.amount(), used.point());
            assertEquals(initialUserPoint.point() - request.amount(), used.point());
        }

        @Test
        @DisplayName("잔액 부족시 예외가 발생한다")
        void 포인트사용_잔고부족_예외() {
            UserPoint initialUserPoint = pointRepository.insertOrUpdate(1L, 500L);
            assertEquals(500L, initialUserPoint.point());

            assertThrows(IllegalArgumentException.class, () -> {
                pointService.use(PointSteps.포인트사용요쳥_생성(1L, 1000L));
            });

        }

        @Test
        @DisplayName("사용 내역이 히스토리에 저장된다")
        void 포인트사용_히스토리() {
            UserPoint initialUserPoint = pointRepository.insertOrUpdate(1L, 1000L);
            final UsePointRequest request = PointSteps.포인트사용요쳥_생성(1L, 500L);

            UsePointResponse used = pointService.use(request);

            assertEquals(initialUserPoint.point() - request.amount(), used.point());
            List<PointHistory> historyList = pointHistoryRepository.selectAllByUserId(used.userId());
            assertEquals(1, historyList.size());
            assertEquals(request.amount(), historyList.get(0).amount());
            assertEquals(TransactionType.USE, historyList.get(0).type());
        }
    }

    @Nested
    @DisplayName("포인트 조회 시")
    class GetPoint {
        @Test
        @DisplayName("포인트 충전 후, 포인트가 정상적으로 조회한다")
        void 포인트조회() {
            final ChargePointRequest request = PointSteps.포인트충전요청_생성(1L, 1000L);
            pointService.charge(request);
            final long userId = 1L;

            GetPointResponse response =  pointService.get(userId);

            assertEquals(userId, response.userId());
            assertEquals(request.amount(), response.point());
        }

        @Test
        @DisplayName("충전 이력이 없는 유저는 포인트가 0으로 조회된다")
        void 포인트조회_이력없음() {
            final long userId = 999L;

            GetPointResponse response =  pointService.get(userId);

            assertEquals(userId, response.userId());
            assertEquals(0L, response.point());
        }
    }
}
