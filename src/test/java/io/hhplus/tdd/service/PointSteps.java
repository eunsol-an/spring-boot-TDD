package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.dto.ChargePointRequest;
import io.hhplus.tdd.controller.dto.UsePointRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.concurrent.ThreadLocalRandom;

public class PointSteps {
    public static ChargePointRequest 포인트충전요청_생성(long userId, long amount) {
        return new ChargePointRequest(userId, amount);
    }

    public static UsePointRequest 포인트사용요쳥_생성(long userId, long amount) {
        return new UsePointRequest(userId, amount);
    }

    public static ExtractableResponse<Response> 포인트충전요청(ChargePointRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/point/charge")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 포인트사용요청(UsePointRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/point/use")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 포인트조회요청(long userId) {
        return RestAssured.given().log().all()
                .when()
                .get("/point/{userId}", userId)
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 포인트내역조회요청(long userId) {
        return RestAssured.given().log().all()
                .when()
                .get("/point/{userId}/histories", userId)
                .then()
                .log().all().extract();
    }

    // 랜덤한 userId를 생성 (1000 ~ 9999)
    public static long 랜덤유저아이디_생성() {
        return ThreadLocalRandom.current().nextLong(1000, 10_000);
    }
}