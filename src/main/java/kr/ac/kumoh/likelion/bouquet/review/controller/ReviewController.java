package kr.ac.kumoh.likelion.bouquet.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderResponse;
import kr.ac.kumoh.likelion.bouquet.review.dto.ReviewCreateRequest;
import kr.ac.kumoh.likelion.bouquet.review.dto.ReviewResponse;
import kr.ac.kumoh.likelion.bouquet.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "꽃집")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(
            summary = "리뷰 작성",
            description = "꽃집에 대한 리뷰를 작성합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ReviewResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃집에 대한 리뷰 작성을 성공했습니다.",
                    response = ReviewResponse.class
            )
    )
    @PostMapping("/shops/{shopId}/reviews")
    public ResponseEntity<ResponseBody<ReviewResponse>> createReview(@CurrentUserId Long userId,
                                                   @PathVariable("shopId") Long shopId,
                                                   @RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtils.createSuccessResponse(reviewService.createReview(userId, shopId, reviewCreateRequest)));
    }

    @Operation(
            summary = "리뷰 목록 조회",
            description = "꽃집에 대한 리뷰를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃집에 대한 리뷰 조회에 성공했습니다.",
                    response = OrderResponse[].class
            )
    )
    @GetMapping("/shops/{shopId}/reviews")
    public ResponseEntity<ResponseBody<List<ReviewResponse>>> getReviews(@PathVariable("shopId") Long shopId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(reviewService.getReviews(shopId)));
    }
}
