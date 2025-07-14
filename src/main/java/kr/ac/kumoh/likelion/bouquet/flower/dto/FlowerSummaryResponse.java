package kr.ac.kumoh.likelion.bouquet.flower.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlowerSummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String flowerLanguage;
    private Integer price;
}
