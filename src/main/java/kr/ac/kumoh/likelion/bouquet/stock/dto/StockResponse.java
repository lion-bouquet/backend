package kr.ac.kumoh.likelion.bouquet.stock.dto;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;

import java.time.LocalDateTime;

public record StockResponse(
    Long stockId,
    Flower flower,
    Long price,
    Boolean status,
    LocalDateTime available
) {
}
