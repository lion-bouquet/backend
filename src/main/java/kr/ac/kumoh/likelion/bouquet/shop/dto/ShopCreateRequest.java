package kr.ac.kumoh.likelion.bouquet.shop.dto;

import kr.ac.kumoh.likelion.bouquet.shop.domain.BusinessHour;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.domain.ShopBusinessHour;
import kr.ac.kumoh.likelion.bouquet.shop.domain.ShopImage;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class ShopCreateRequest {
    private String loginId;
    private String password;
    private String email;
    private String ownerName;
    private String shopName;
    private String phoneNumber;
    private String shopImageUrl;
    private List<String> images;
    private String province;
    private String city;
    private String introduction;
    private List<ShopDetailResponse.BusinessHourDto> businessHours;

    public FlowerShop toEntity() {
        FlowerShop shop = FlowerShop.builder()
                .loginId(loginId)
                .password(password) // 서비스에서 인코딩 필요
                .email(email)
                .ownerName(ownerName)
                .shopName(shopName)
                .phoneNumber(phoneNumber)
                .shopImageUrl(shopImageUrl)
                .province(province)
                .city(city)
                .introduction(introduction)
                .rating(0.0)
                .build();

        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                ShopImage image = ShopImage.builder()
                        .shop(shop)
                        .url(images.get(i))
                        .sortOrder(i + 1)
                        .build();
                shop.getImages().add(image);
            }
        }

        if (businessHours != null) {
            List<ShopBusinessHour> hours = businessHours.stream()
                    .map(dto -> ShopBusinessHour.builder()
                            .shop(shop)
                            .dayOfWeek(dto.getDayOfWeek())
                            .businessHour(BusinessHour.builder()
                                    .open(LocalTime.parse(dto.getOpenTime()))
                                    .close(LocalTime.parse(dto.getCloseTime()))
                                    .build())
                            .build())
                    .toList();

            shop.getBusinessHours().addAll(hours);
        }

        return shop;
    }
}
