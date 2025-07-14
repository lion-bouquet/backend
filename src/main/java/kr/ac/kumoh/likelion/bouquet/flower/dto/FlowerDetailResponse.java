package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.flower.domain.FlowerSize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FlowerDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String flowerLanguage;
    private Boolean isPositive;
    private List<ColorResponse> matchingColors;
    private String seasonStart;
    private String seasonEnd;
    private FlowerSize size;
    private String imageUrl;
    private String species;
    private String origin;
    private String scent;
    private String lifespan;
}