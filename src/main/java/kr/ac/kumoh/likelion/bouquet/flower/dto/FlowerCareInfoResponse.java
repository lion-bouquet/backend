package kr.ac.kumoh.likelion.bouquet.flower.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FlowerCareInfoResponse {
    private String sunlight;
    private String watering;
    private String soil;
    private String temperature;
    private String pruning;
    private String pestsDiseases;
}
