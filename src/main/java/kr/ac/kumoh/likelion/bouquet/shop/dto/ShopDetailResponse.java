package kr.ac.kumoh.likelion.bouquet.shop.dto;

import kr.ac.kumoh.likelion.bouquet.shop.domain.BusinessHour;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.domain.ShopBusinessHour;
import kr.ac.kumoh.likelion.bouquet.shop.domain.ShopImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShopDetailResponse {
    private Long id;
    private String shopName;
    private String ownerName;
    private String phoneNumber;
    private String shopImage;
    private List<String> images;
    private String province;
    private String city;
    private String introduction;
    private Double rating;
    private Boolean open;
    private List<BusinessHourDto> businessHours;
    private Integer reviewCount;

    public static ShopDetailResponse from(FlowerShop shop, boolean open, int reviewCount) {
        return ShopDetailResponse.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .ownerName(shop.getOwnerName())
                .phoneNumber(shop.getPhoneNumber())
                .shopImage(shop.getShopImageUrl())
                .images(shop.getImages().stream().map(ShopImage::getUrl).toList())
                .province(shop.getProvince())
                .city(shop.getCity())
                .introduction(shop.getIntroduction())
                .rating(shop.getRating())
                .open(open)
                .businessHours(
                        shop.getBusinessHours()
                                .stream()
                                .map(BusinessHourDto::from)
                                .collect(Collectors.toList()))
                .reviewCount(reviewCount)
                .build();
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class BusinessHourDto {
        private DayOfWeek dayOfWeek;
        private String openTime;
        private String closeTime;

        private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("HH:mm");

        public static BusinessHourDto from(ShopBusinessHour sbh) {
            BusinessHour bh = sbh.getBusinessHour();
            return BusinessHourDto.builder()
                    .dayOfWeek(sbh.getDayOfWeek())
                    .openTime(bh.getOpen().format(F))
                    .closeTime(bh.getClose().format(F))
                    .build();
        }
    }
}
