package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
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
    private String floriography;
    private Boolean isPositive;
    private List<ColorResponse> matchingColors;
    private Integer seasonStart;
    private Integer seasonEnd;
    private FlowerSize size;
    private String imageUrl;
    private String species;
    private String family;
    private String origin;
    private String scent;
    private String lifespan;
    private FlowerCareInfoResponse careInfo;

    public static FlowerDetailResponse from(Flower flower, List<ColorResponse> matchingColors) {
        return FlowerDetailResponse.builder()
                .id(flower.getId())
                .name(flower.getName())
                .description(flower.getDescription())
                .floriography(flower.getFloriography())
                .isPositive(flower.getIsPositive())
                .matchingColors(matchingColors)
                .seasonStart(flower.getSeasonStart())
                .seasonEnd(flower.getSeasonEnd())
                .size(flower.getSize())
                .imageUrl(flower.getImageUrl())
                .species(flower.getSpecies())
                .origin(flower.getOrigin())
                .scent(flower.getScent())
                .lifespan(flower.getLifespan())
                .careInfo(FlowerCareInfoResponse.from(flower.getCareInfo()))
                .build();
    }
}
