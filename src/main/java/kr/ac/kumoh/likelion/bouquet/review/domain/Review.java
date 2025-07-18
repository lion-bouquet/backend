package kr.ac.kumoh.likelion.bouquet.review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private FlowerShop shop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Float stars;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public Review(FlowerShop shop, User user, Float stars, String content, LocalDateTime createdAt) {
        this.shop = shop;
        this.user = user;
        this.stars = stars;
        this.content = content;
        this.createdAt = createdAt;
    }
}
