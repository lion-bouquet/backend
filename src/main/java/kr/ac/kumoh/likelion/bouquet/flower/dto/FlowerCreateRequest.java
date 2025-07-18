// FlowerCreateRequest.java

package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import kr.ac.kumoh.likelion.bouquet.flower.domain.FlowerSize;
import lombok.Getter;

@Getter
public class FlowerCreateRequest {
    private String name;
    private String description;
    private String flowerLanguage;
    private Boolean isPositive;
    private String seasonStart;
    private String seasonEnd;
    private FlowerSize size;
    private String imageUrl;
    private String species;
    private String origin;
    private String scent;
    private String lifespan;

    public Flower toEntity() {
        return Flower.builder()
                .name(this.name)
                .description(this.description)
                .flowerLanguage(this.flowerLanguage)
                .isPositive(this.isPositive)
                .seasonStart(this.seasonStart)
                .seasonEnd(this.seasonEnd)
                .size(this.size)
                .imageUrl(this.imageUrl)
                .species(this.species)
                .origin(this.origin)
                .scent(this.scent)
                .lifespan(this.lifespan)
                .build();
    }
}