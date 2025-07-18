package kr.ac.kumoh.likelion.bouquet.shop.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.review.repository.ReviewRepository;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopCreateRequest;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopDetailResponse;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository; // 리뷰 기능 구현을 위해 추가

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


    public ShopDetailResponse findShopById(Long shopId) {
        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        // reviewRepository를 통해 실제 리뷰 개수를 조회
        long reviewCount = reviewRepository.countByShop(shop);

        return ShopDetailResponse.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .address(shop.getAddress())
                .introduction(shop.getIntroduction())
                .rating(shop.getRating())
                .openingHoursWeekdays(shop.getOpeningHoursWeekdays())
                .openingHoursWeekend(shop.getOpeningHoursWeekend())
                .reviewCount((int) reviewCount)
                .build();
    }

    private String checkOpenStatus(FlowerShop shop) {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        String openingHoursStr;
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            openingHoursStr = shop.getOpeningHoursWeekend();
        } else {
            openingHoursStr = shop.getOpeningHoursWeekdays();
        }

        if (openingHoursStr == null || openingHoursStr.isBlank() || !openingHoursStr.contains("-")) {
            return "정보 없음";
        }

        try {
            String[] parts = openingHoursStr.split("-");
            String openTimeStr = parts[0].trim();
            String closeTimeStr = parts[1].trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime openTime = LocalTime.parse(openTimeStr, formatter);
            LocalTime closeTime = LocalTime.parse(closeTimeStr, formatter);

            // 24시 영업 또는 종료 시간이 다음날인 경우 (예: 10:00 - 02:00)
            if (closeTime.isBefore(openTime)) {
                if (currentTime.isAfter(openTime) || currentTime.isBefore(closeTime)) {
                    return "영업 중";
                }
            } else { // 일반적인 경우
                if (currentTime.isAfter(openTime) && currentTime.isBefore(closeTime)) {
                    return "영업 중";
                }
            }
            return "영업 종료";

        } catch (DateTimeParseException e) {
            log.error("영업시간 파싱 오류. shopId: {}, openingHours: {}", shop.getId(), openingHoursStr, e);
            return "정보 없음";
        }
    }

    @Transactional
    public void createShop(ShopCreateRequest request) {
        FlowerShop shop = request.toEntity(); // DTO를 Entity로 변환
        shopRepository.save(shop);
    }
}
