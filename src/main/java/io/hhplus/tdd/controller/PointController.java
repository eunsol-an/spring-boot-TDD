package io.hhplus.tdd.controller;

import io.hhplus.tdd.controller.dto.*;
import io.hhplus.tdd.service.PointService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public ResponseEntity<GetPointResponse> point(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(pointService.get(id));
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public ResponseEntity<List<GetPointHistoryResponse>> history(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(pointService.getHistoryList(id));
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("/charge")
    public ResponseEntity<ChargePointResponse> charge(
            @RequestBody ChargePointRequest request
    ) {
        return ResponseEntity.ok(pointService.charge(request));
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("/use")
    public ResponseEntity<UsePointResponse> use(
            @RequestBody UsePointRequest request
    ) {
        return ResponseEntity.ok(pointService.use(request));
    }
}
