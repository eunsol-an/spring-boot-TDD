package io.hhplus.tdd.controller.dto;

import io.hhplus.tdd.point.TransactionType;

public record GetPointHistoryResponse(long id, long userId, long amount, TransactionType type, long updateMillis) {
}
