package kr.ac.kumoh.likelion.bouquet.flower.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerDetailResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "꽃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/flowers")
public class FlowerController {

    private final FlowerService flowerService;

    @GetMapping
    public ResponseEntity<List<FlowerSummaryResponse>> getFlowers(
            @RequestParam(required = false) Long colorId,
            @RequestParam(required = false) Integer month) {
        List<FlowerSummaryResponse> flowers = flowerService.findFlowers(colorId, month);
        return ResponseEntity.ok(flowers);
    }

    @GetMapping("/{flowerId}")
    public ResponseEntity<FlowerDetailResponse> getFlowerDetail(@PathVariable Long flowerId) {
        FlowerDetailResponse flower = flowerService.findFlowerById(flowerId);
        return ResponseEntity.ok(flower);
    }

    @GetMapping("/{flowerId}/matching-colors")
    public ResponseEntity<List<ColorResponse>> getMatchingColors(@PathVariable Long flowerId) {
        List<ColorResponse> colors = flowerService.findMatchingColorsByFlowerId(flowerId);
        return ResponseEntity.ok(colors);
    }
}
