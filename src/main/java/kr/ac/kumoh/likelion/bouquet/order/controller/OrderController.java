package kr.ac.kumoh.likelion.bouquet.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerDetailResponse;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderRequest;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderResponse;
import kr.ac.kumoh.likelion.bouquet.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(
            summary = "주문",
            description = "꽃집에서 꽃들을 주문합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "주문에 성공했습니다.",
                    response = OrderResponse.class
            )
    )
    @PostMapping
    public ResponseEntity<ResponseBody<OrderResponse>> createOrder(@CurrentUserId Long userId,
                                                            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtils.createSuccessResponse(orderService.createOrder(userId, orderRequest)));
    }

    @Operation(
            summary = "주문 내역 조회",
            description = "사용자의 주문 내역을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "사용자에 대한 주문 내역 조회에 성공했습니다.",
                    response = OrderResponse[].class
            )
    )
    @GetMapping
    public ResponseEntity<ResponseBody<List<OrderResponse>>> getOrders(@CurrentUserId Long userId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(orderService.getOrdersByUser(userId)));
    }

    @Operation(
            summary = "주문 정보 조회",
            description = "주문 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "주문 정보 조회에 성공했습니다.",
                    response = OrderResponse.class
            )
    )
    @GetMapping("/{code}")
    public ResponseEntity<ResponseBody<OrderResponse>> getOrder(@CurrentUserId Long userId,
                                                                @PathVariable("code") String orderCode) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(orderService.getOrder(userId, orderCode)));
    }

    @Operation(
            summary = "주문 취소",
            description = "주문을 취소합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    status = HttpStatus.NO_CONTENT,
                    description = "주문 취소에 성공했습니다."
            )
    )
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBody<Void>> cancel(@CurrentUserId Long userId,
                                                     @PathVariable("id") Long orderId) {
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}
