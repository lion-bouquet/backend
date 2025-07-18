package kr.ac.kumoh.likelion.bouquet.stock.service;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import kr.ac.kumoh.likelion.bouquet.flower.repository.FlowerRepository;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import kr.ac.kumoh.likelion.bouquet.stock.domain.Stock;
import kr.ac.kumoh.likelion.bouquet.stock.dto.StockResponse;
import kr.ac.kumoh.likelion.bouquet.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final ShopRepository shopRepository;
    private final FlowerRepository flowerRepository;
    private final StockRepository stockRepository;

    public List<StockResponse> getStocks(Long shopId) {
        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        List<Stock> stocks = stockRepository.findByShop(shop);

        return stocks.stream().map((stock) -> {
            Flower flower = flowerRepository.findById(stock.getFlower().getId())
                    .orElseThrow(() -> new ServiceException(ErrorCode.FLOWER_NOT_FOUND));

            return new StockResponse(stock.getId(),
                    flower,
                    stock.getPrice(),
                    stock.getStatus(),
                    stock.getAvailableDateTime());
        }).toList();
    }
}
