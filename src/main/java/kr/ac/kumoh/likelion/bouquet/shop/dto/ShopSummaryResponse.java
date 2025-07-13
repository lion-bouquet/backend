package kr.ac.kumoh.likelion.bouquet.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopSummaryResponse {
    private Long id;
    private String shopName;
    private String address;
    private Double rating;
    private String openStatus;
}