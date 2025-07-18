package kr.ac.kumoh.likelion.bouquet.shop.dto;

import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShopSummaryResponse {
    private Long id;
    private String shopName;
    private String shopImage;
    private String province;
    private String city;
    private Double rating;
    private Boolean openStatus;

    public static ShopSummaryResponse from(FlowerShop shop, LocalDateTime localDateTime) {
        return new ShopSummaryResponse(shop.getId(), shop.getShopName(), shop.getShopImageUrl(), shop.getProvince(), shop.getCity(), shop.getRating(), shop.isOpenNow(localDateTime));
    }
}
