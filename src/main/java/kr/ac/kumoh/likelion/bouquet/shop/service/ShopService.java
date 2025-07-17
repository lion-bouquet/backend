package kr.ac.kumoh.likelion.bouquet.shop.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    // private final ReviewRepository reviewRepository; // TODO: 리뷰 기능 구현 시 추가

    /**
     * @param name (선택) 검색할 꽃집 이름
     * @return 꽃집 요약 정보 리스트
     */
    public List<ShopSummaryResponse> findShops(String name) {
        // 이름 파라미터가 비어있지 않으면 이름으로 검색, 비어있으면 전체 조회
        List<FlowerShop> shops = (name != null && !name.isBlank())
                ? shopRepository.findByShopNameContaining(name)
                : shopRepository.findAll();

        // 조회된 엔티티 리스트를 DTO 리스트로 변환
        return shops.stream().map(shop -> {
            String openStatus = checkOpenStatus(shop); // 영업 상태 확인
            return ShopSummaryResponse.builder()
                    .id(shop.getId())
                    .shopName(shop.getShopName())
                    .address(shop.getAddress())
                    .rating(shop.getRating())
                    .openStatus(openStatus)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * @param shopId 조회할 꽃집의 ID
     * @return 꽃집 상세 정보
     */
    public ShopDetailResponse findShopById(Long shopId) {
        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        // TODO: 추후 리뷰 기능이 구현되면 reviewRepository를 통해 실제 리뷰 개수를 조회해야 합니다.
        int reviewCount = 0;

        return ShopDetailResponse.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .address(shop.getAddress())
                .introduction(shop.getIntroduction())
                .rating(shop.getRating())
                .openingHoursWeekdays(shop.getOpeningHoursWeekdays())
                .openingHoursWeekend(shop.getOpeningHoursWeekend())
                .reviewCount(reviewCount)
                .build();
    }

    private String checkOpenStatus(FlowerShop shop) {
        // TODO: shop의 openingHours 필드와 현재 시간을 비교하여 "Open" 또는 "Closed" 반환 로직 구현
        return "Open"; // 예시
    }
}
