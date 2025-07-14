package kr.ac.kumoh.likelion.bouquet.shop.controller;

import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopSummaryResponse>> getShops(@RequestParam(required = false) String name) {
        List<ShopSummaryResponse> shops = shopService.findShops(name);
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponse> getShopDetail(@PathVariable Long shopId) {
        ShopDetailResponse shop = shopService.findShopById(shopId);
        return ResponseEntity.ok(shop);
    }
}
