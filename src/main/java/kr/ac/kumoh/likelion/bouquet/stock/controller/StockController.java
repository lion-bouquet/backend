package kr.ac.kumoh.likelion.bouquet.stock.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.stock.dto.StockResponse;
import kr.ac.kumoh.likelion.bouquet.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "꽃집")
@Controller
@RequiredArgsConstructor
@RequestMapping("/shops")
public class StockController {
    private final StockService stockService;

    @GetMapping("/{id}/stocks")
    public ResponseEntity<ResponseBody<List<StockResponse>>> getStocks(@PathVariable("id") Long stockId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(stockService.getStocks(stockId)));
    }
}
