package kr.ac.kumoh.likelion.bouquet.order.dto;

import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.order.domain.OrderDetail;

public record OrderDetailResponse(
        Long stockId,
        FlowerSummaryResponse flower,
        Integer unitPrice,
        Integer quantity
) {
    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getId(),
                FlowerSummaryResponse.from(orderDetail.getStock().getFlower()),
                orderDetail.getUnitPrice(),
                orderDetail.getQuantity());
    }
}

