package kr.ac.kumoh.likelion.bouquet.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopCreateRequest;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "꽃집")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    @Operation(
            summary = "꽃집 목록 조회",
            description = "꽃집 목록을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ShopSummaryResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃집 목록 조회에 성공했습니다.",
                    response = ShopSummaryResponse[].class
            )
    )
    @GetMapping
    public ResponseEntity<List<ShopSummaryResponse>> getShops(@RequestParam(required = false) String name) {
        List<ShopSummaryResponse> shops = shopService.findShops(name);
        return ResponseEntity.ok(shops);
    }

    @Operation(
            summary = "꽃집 조회",
            description = "꽃집 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ShopDetailResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃집 정보 조회에 성공했습니다.",
                    response = ShopDetailResponse.class
            )
    )
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponse> getShopDetail(@PathVariable Long shopId) {
        ShopDetailResponse shop = shopService.findShopById(shopId);
        return ResponseEntity.ok(shop);
    }

    @Operation(
            summary = "추천 꽃집 조회",
            description = "추천 꽃집 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ShopSummaryResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "추천 꽃집 정보 조회에 성공했습니다.",
                    response = ShopSummaryResponse[].class
            )
    )
    @GetMapping("/recommended")
    public ResponseEntity<List<ShopSummaryResponse>> getRecommendedShops(@RequestParam(value = "size", defaultValue = "6") int size) {
        return ResponseEntity.ok(shopService.findRandomShops(size));
    }

    @PostMapping
    public ResponseEntity<Void> createShop(@RequestBody ShopCreateRequest request) {
        shopService.createShop(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
