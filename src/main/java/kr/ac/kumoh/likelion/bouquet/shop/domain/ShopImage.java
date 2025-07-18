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
@Table(name = "shop_image")
public class ShopImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private FlowerShop shop;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    private Integer sortOrder;

    private String altText;

    @Builder
    public ShopImage(FlowerShop shop, String url, Integer sortOrder, String altText) {
        this.shop = shop;
        this.url = url;
        this.sortOrder = sortOrder;
        this.altText = altText;
    }
}
