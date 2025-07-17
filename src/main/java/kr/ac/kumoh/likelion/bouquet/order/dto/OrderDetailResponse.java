package kr.ac.kumoh.likelion.bouquet.order.dto;

import kr.ac.kumoh.likelion.bouquet.order.domain.OrderDetail;

public record OrderDetailResponse(
        Long stockId,
        Integer unitPrice,
        Integer quantity
) {
    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getId(), orderDetail.getUnitPrice(), orderDetail.getQuantity());
    }
}

