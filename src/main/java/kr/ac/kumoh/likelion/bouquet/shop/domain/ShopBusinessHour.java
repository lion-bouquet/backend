package kr.ac.kumoh.likelion.bouquet.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shop_business_hour",
        uniqueConstraints = @UniqueConstraint(columnNames = { "shop_id","day_of_week" }))
public class ShopBusinessHour {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 꽃집
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private FlowerShop shop;

    // 요일
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 3)
    private DayOfWeek dayOfWeek;

    // 개점 및 폐점 시간
    @Embedded
    private BusinessHour businessHour;

    @Builder
    public ShopBusinessHour(FlowerShop shop, DayOfWeek dayOfWeek, BusinessHour businessHour) {
        this.shop = shop;
        this.dayOfWeek = dayOfWeek;
        this.businessHour = businessHour;
    }
}
