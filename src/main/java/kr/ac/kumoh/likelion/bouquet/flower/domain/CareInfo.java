package kr.ac.kumoh.likelion.bouquet.flower.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareInfo {
    // 햇빛 요구 조건
    @Column(name = "care_sunlight", length = 700)
    private String sunlight;

    // 물 주기
    @Column(name = "care_watering", length = 700)
    private String watering;

    // 토양 선호도
    @Column(name = "care_soil", length = 700)
    private String soil;

    // 온도 및 기후
    @Column(name = "care_temperature", length = 700)
    private String temperature;

    // 손질 방법
    @Column(name = "care_pruning", length = 700)
    private String pruning;

    // 해충 & 질병
    @Column(name = "care_pests_diseases", length = 700)
    private String pestsDiseases;

    @Builder
    public CareInfo(String sunlight, String watering, String soil, String temperature, String pruning, String pestsDiseases) {
        this.sunlight = sunlight;
        this.watering = watering;
        this.soil = soil;
        this.temperature = temperature;
        this.pruning = pruning;
        this.pestsDiseases = pestsDiseases;
    }
}
