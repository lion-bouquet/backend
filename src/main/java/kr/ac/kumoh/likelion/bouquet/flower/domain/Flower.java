package kr.ac.kumoh.likelion.bouquet.flower.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    private String flowerLanguage;

    private Boolean isPositive;

    private String seasonStart;
    private String seasonEnd;

    @Enumerated(EnumType.STRING)
    private FlowerSize size;

    private String imageUrl;

    private String species;      // 종
    private String origin;       // 원산지
    private String scent;        // 향기
    private String lifespan;     // 수명
}