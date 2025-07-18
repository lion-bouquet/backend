package kr.ac.kumoh.likelion.bouquet.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.stock.dto.StockResponse;
import kr.ac.kumoh.likelion.bouquet.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "꽃집")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class StockController {
    private final StockService stockService;

    @Operation(
            summary = "꽃집 판매 상품 목록 조회",
            description = "꽃집에서 판매하는 상품 목록을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = StockResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃집 판매 상품 목록 조회에 성공했습니다.",
                    response = StockResponse[].class
            )
    )
    @GetMapping("/{id}/stocks")
    public ResponseEntity<ResponseBody<List<StockResponse>>> getStocks(@PathVariable("id") Long stockId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(stockService.getStocks(stockId)));
    }
}
