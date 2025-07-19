package kr.ac.kumoh.likelion.bouquet.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 꽃집
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private FlowerShop shop;

    // 고객
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 별점
    @Column(name = "stars")
    private Float stars;

    // 내용
    @Column(name = "content", length = 400)
    private String content;

    // 생성 일자
    @Column(name = "created_at", nullable = false)
    @CreatedDate
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
