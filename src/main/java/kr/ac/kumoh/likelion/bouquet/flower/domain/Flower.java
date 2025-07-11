package kr.ac.kumoh.likelion.bouquet.flower.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.likelion.bouquet.color.domain.Color;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flower_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Lob
    private String description;

    @Column(nullable = false)
    private String flowerLanguage;

    @Column(nullable = false)
    private Boolean isPositive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(nullable = false, length = 5)
    private String seasonStart;

    @Column(nullable = false, length = 5)
    private String seasonEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlowerSize size;

    private String imageUrl;
}
