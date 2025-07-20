package kr.ac.kumoh.likelion.bouquet.flower.dto;

import kr.ac.kumoh.likelion.bouquet.flower.domain.CareInfo;
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

    public static FlowerCareInfoResponse from(CareInfo careInfo) {
        return new FlowerCareInfoResponse(careInfo.getSunlight(),
                careInfo.getWatering(),
                careInfo.getSoil(),
                careInfo.getTemperature(),
                careInfo.getPruning(),
                careInfo.getPestsDiseases());
    }
}
