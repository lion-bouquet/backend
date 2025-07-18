package kr.ac.kumoh.likelion.bouquet.flower.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerCreateRequest;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerDetailResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.flower.service.FlowerService;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "꽃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/flowers")
public class FlowerController {

    private final FlowerService flowerService;

    @Operation(
            summary = "꽃 목록 조회",
            description = "꽃 목록을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ColorResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃 목록 조회에 성공했습니다.",
                    response = ColorResponse[].class
            )
    )
    @GetMapping
    public ResponseEntity<ResponseBody<List<FlowerSummaryResponse>>> getFlowers(
            @RequestParam(required = false) Long colorId,
            @RequestParam(required = false) Integer month) {
        List<FlowerSummaryResponse> flowers = flowerService.findFlowers(colorId, month);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(flowers));
    }

    @Operation(
            summary = "꽃 정보 조회",
            description = "꽃 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = FlowerDetailResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃 정보 조회에 성공했습니다.",
                    response = FlowerDetailResponse.class
            )
    )
    @GetMapping("/{flowerId}")
    public ResponseEntity<FlowerDetailResponse> getFlowerDetail(@PathVariable Long flowerId) {
        FlowerDetailResponse flower = flowerService.findFlowerById(flowerId);
        return ResponseEntity.ok(flower);
    }

    @Operation(
            summary = "꽃에 어울리는 색상 목록 조회",
            description = "꽃에 어울리는 색상 목록을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = FlowerDetailResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃에 어울리는 색상 목록 조회에 성공했습니다.",
                    response = FlowerDetailResponse[].class
            )
    )
    @GetMapping("/{flowerId}/matching-colors")
    public ResponseEntity<List<ColorResponse>> getMatchingColors(@PathVariable Long flowerId) {
        List<ColorResponse> colors = flowerService.findMatchingColorsByFlowerId(flowerId);
        return ResponseEntity.ok(colors);
    }

    @PostMapping
    public ResponseEntity<Void> createFlower(@RequestBody FlowerCreateRequest request) {
        flowerService.createFlower(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
