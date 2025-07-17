package kr.ac.kumoh.likelion.bouquet.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderRequest;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderResponse;
import kr.ac.kumoh.likelion.bouquet.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "주문")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseBody<OrderResponse>> save(@CurrentUserId Long userId,
                                                            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(orderService.save(userId, orderRequest)));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<OrderResponse>>> getOrders(@CurrentUserId Long userId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(orderService.getOrdersByUser(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<OrderResponse>> getOrder(@CurrentUserId Long userId,
                                                                @PathVariable("id") Long orderId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(orderService.getOrder(userId, orderId)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBody<Void>> cancel(@CurrentUserId Long userId,
                                                     @PathVariable("id") Long orderId) {
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse());
    }
}
