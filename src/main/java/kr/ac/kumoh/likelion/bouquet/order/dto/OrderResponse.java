package kr.ac.kumoh.likelion.bouquet.order.dto;

import kr.ac.kumoh.likelion.bouquet.order.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        LocalDateTime orderDate,
        Long totalPrice,
        String status,
        List<OrderDetailResponse> stocks
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getStatus().toString(),
                order.getOrderDetails().stream().map(OrderDetailResponse::from).toList());
    }
}
