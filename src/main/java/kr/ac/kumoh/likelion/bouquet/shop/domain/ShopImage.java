package kr.ac.kumoh.likelion.bouquet.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shop_images")
public class ShopImage {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 꽃집
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private FlowerShop shop;

    // 꽃집 사진
    @Column(name = "image_url", nullable = false, length = 2048)
    private String url;

    // 사진 우선순위
    @Column(name = "sort_order")
    private Integer sortOrder;

    // 대체 텍스트
    @Column(name = "alt_text")
    private String altText;

    @Builder
    public ShopImage(FlowerShop shop, String url, Integer sortOrder, String altText) {
        this.shop = shop;
        this.url = url;
        this.sortOrder = sortOrder;
        this.altText = altText;
    }
}
