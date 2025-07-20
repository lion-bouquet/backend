package kr.ac.kumoh.likelion.bouquet.order.dto;

import kr.ac.kumoh.likelion.bouquet.order.domain.Order;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String orderCode,
        LocalDateTime orderDate,
        Long totalPrice,
        String status,
        ShopSummaryResponse shop,
        LocalDateTime pickUpAvailableTime,
        String phone,
        String request,
        List<OrderDetailResponse> stocks
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getOrderCode(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getStatus().toString(),
                ShopSummaryResponse.from(order.getShop(), LocalDateTime.now()),
                order.getAvailDate(),
                order.getPhoneNumber(),
                order.getContent(),
                order.getOrderDetails().stream().map(OrderDetailResponse::from).toList());
    }
}
