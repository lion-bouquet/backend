package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.flower.domain.CareInfo;
import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import kr.ac.kumoh.likelion.bouquet.flower.domain.FlowerSize;
import lombok.Getter;

@Getter
public class FlowerCreateRequest {
    private String name;
    private String description;
    private String floriography;
    private Boolean isPositive;
    private Integer seasonStart;
    private Integer seasonEnd;
    private FlowerSize size;
    private String imageUrl;
    private String species;
    private String family;
    private String origin;
    private String scent;
    private String lifespan;
    private String sunlight;
    private String watering;
    private String soil;
    private String temperature;
    private String pruning;
    private String pestsDiseases;

    public Flower toEntity() {
        return Flower.builder()
                .name(this.name)
                .description(this.description)
                .floriography(this.floriography)
                .isPositive(this.isPositive)
                .seasonStart(this.seasonStart)
                .seasonEnd(this.seasonEnd)
                .size(this.size)
                .imageUrl(this.imageUrl)
                .species(this.species)
                .family(this.family)
                .origin(this.origin)
                .scent(this.scent)
                .lifespan(this.lifespan)
                .careInfo(CareInfo.builder()
                        .sunlight(this.sunlight)
                        .watering(this.watering)
                        .soil(this.soil)
                        .temperature(this.temperature)
                        .pruning(this.pruning)
                        .pestsDiseases(this.pestsDiseases)
                        .build())
                .build();
    }
}
