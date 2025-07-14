package kr.ac.kumoh.likelion.bouquet.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopDetailResponse {
    private Long id;
    private String shopName;
    private String address;
    private String introduction;
    private Double rating;
    private String openingHoursWeekdays;
    private String openingHoursWeekend;
    private int reviewCount;
}