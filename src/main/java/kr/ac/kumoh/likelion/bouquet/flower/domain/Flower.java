package kr.ac.kumoh.likelion.bouquet.flower.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "flowers")
public class Flower {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 꽃 이름
    @Column(name = "name", nullable = false)
    private String name;

    // 꽃 설명
    @Lob
    @Column(name = "description", length = 65535)
    private String description;

    // 꽃말
    @Column(name = "floriography", length = 300)
    private String floriography;

    // 꽃말의 긍부정 여부
    // 참이면 긍정적인 꽃말, 거짓이면 부정적인 꽃말
    @Column(name = "is_positive")
    private Boolean isPositive;

    // 꽃이 피는 시기
    @Column(name = "season_start")
    private Integer seasonStart;

    // 꽃이 지는 시기
    @Column(name = "season_end")
    private Integer seasonEnd;

    // 꽃 크기
    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private FlowerSize size;

    // 꽃 사진
    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    // 종
    @Column(name = "species")
    private String species;

    // 과
    @Column(name = "family")
    private String family;

    // 원산지
    @Column(name = "origin")
    private String origin;

    // 향기
    @Column(name = "scent")
    private String scent;

    // 수명
    @Column(name = "lifespan")
    private String lifespan;

    // 관리 방법
    @Embedded
    private CareInfo careInfo;

    @Builder
    public Flower(String name,
                  String description,
                  String floriography,
                  Boolean isPositive,
                  Integer seasonStart,
                  Integer seasonEnd,
                  FlowerSize size,
                  String imageUrl,
                  String species,
                  String family,
                  String origin,
                  String scent,
                  String lifespan,
                  CareInfo careInfo) {
        this.name = name;
        this.description = description;
        this.floriography = floriography;
        this.isPositive = isPositive;
        this.seasonStart = seasonStart;
        this.seasonEnd = seasonEnd;
        this.size = size;
        this.imageUrl = imageUrl;
        this.species = species;
        this.family = family;
        this.origin = origin;
        this.scent = scent;
        this.lifespan = lifespan;
        this.careInfo = careInfo;
    }
}
