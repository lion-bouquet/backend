package kr.ac.kumoh.likelion.bouquet.shop.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.review.repository.ReviewRepository;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.domain.ShopImage;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopCreateRequest;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository;

    public List<ShopSummaryResponse> findShops(String name) {
        List<FlowerShop> shops = StringUtils.hasText(name)
                ? shopRepository.findByShopNameContaining(name)
                : shopRepository.findAll();

        return shops.stream()
                .map(shop -> ShopSummaryResponse.from(shop, LocalDateTime.now()))
                .toList();
    }

    public List<ShopSummaryResponse> findRandomShops(int size) {
        return shopRepository.findRandomShops(size).stream()
                .map(shop -> ShopSummaryResponse.from(shop, LocalDateTime.now()))
                .toList();
    }

    public ShopDetailResponse findShopById(Long shopId) {
        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        int reviewCount = reviewRepository.countByShop(shop);

        return ShopDetailResponse.from(shop, shop.isOpenNow(LocalDateTime.now()), reviewCount);
    }

    @Transactional
    public void createShop(ShopCreateRequest request) {
        FlowerShop shop = FlowerShop.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .email(request.getEmail())
                .ownerName(request.getOwnerName())
                .shopName(request.getShopName())
                .phoneNumber(request.getPhoneNumber())
                .shopImageUrl(request.getShopImageUrl())
                .province(request.getProvince())
                .city(request.getCity())
                .introduction(request.getIntroduction())
                .rating(0.0)
                .build();

        int order = 1;
        for (String url : request.getImages()) {
            ShopImage image = ShopImage.builder()
                    .shop(shop)
                    .url(url)
                    .sortOrder(++order)
                    .build();
            shop.getImages().add(image);
        }

        shopRepository.save(shop);
    }
}
