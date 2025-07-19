package kr.ac.kumoh.likelion.bouquet.stock.dto;

import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;

import java.time.LocalDateTime;

public record StockResponse(
    Long stockId,
    FlowerSummaryResponse flower,
    Long price,
    Boolean status,
    LocalDateTime available
) {
}
