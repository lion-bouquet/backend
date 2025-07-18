package kr.ac.kumoh.likelion.bouquet.shop.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.likelion.bouquet.review.domain.Review;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlowerShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 30)
    private String ownerName;

    @Column(nullable = false, length = 100)
    private String shopName;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String shopImageUrl;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<ShopImage> images = new ArrayList<>();

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private Double rating = 0.0;

    @Builder
    public FlowerShop(String loginId, String password, String email, String ownerName, String shopName, String phoneNumber, String shopImageUrl, String province, String city, String introduction, Double rating) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.ownerName = ownerName;
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.shopImageUrl = shopImageUrl;
        this.province = province;
        this.city = city;
        this.introduction = introduction;
    }

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopBusinessHour> businessHours = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void recalculateRating() {
        double avg = reviews.stream()
                .mapToDouble(Review::getStars)
                .average()
                .orElse(0.0);
        // 소수점 둘째 자리까지 반올림
        this.rating = Math.round(avg * 100) / 100.0;
    }

    public boolean isOpenNow(LocalDateTime localDateTime) {
        DayOfWeek dow = localDateTime.getDayOfWeek();
        LocalTime now = localDateTime.toLocalTime();

        return businessHours.stream()
                .filter(b -> b.getDayOfWeek() == dow)
                .findFirst()
                .map(b -> {
                    LocalTime open = b.getBusinessHour().getOpen();
                    LocalTime close = b.getBusinessHour().getClose();

                    if (close.isBefore(open)) {
                        return !now.isBefore(open) || !now.isAfter(close);
                    }

                    return !now.isBefore(open) && !now.isAfter(close);
                })
                .orElse(false);
    }
}
