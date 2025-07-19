package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlowerSummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String floriography;

    public static FlowerSummaryResponse from(Flower flower) {
        return FlowerSummaryResponse.builder()
                .id(flower.getId())
                .name(flower.getName())
                .floriography(flower.getFloriography())
                .imageUrl(flower.getImageUrl())
                .build();
    }
}
