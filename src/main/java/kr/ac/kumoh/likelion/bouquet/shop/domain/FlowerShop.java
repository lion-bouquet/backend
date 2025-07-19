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
@Table(name = "shops")
public class FlowerShop {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 꽃집 관리용 아이디
    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    // 꽃집 관리용 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 이메일 주소
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // 꽃집 주인
    @Column(name = "owner_name", nullable = false, length = 30)
    private String ownerName;

    // 꽃집 이름
    @Column(name = "name", nullable = false, length = 100)
    private String shopName;

    // 꽃집 대표번호
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    // 꽃집 대표 이미지
    @Column(name = "image_url", length = 2048)
    private String shopImageUrl;

    // 꽃집 소개 이미지
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<ShopImage> images = new ArrayList<>();

    // 특별시, 광역시, 특별자치시, 도
    @Column(name = "province", nullable = false)
    private String province;

    // 시, 군, 구
    @Column(name = "city", nullable = false)
    private String city;

    // 소개 문구
    @Lob
    @Column(name = "introduction", length = 65535)
    private String introduction;

    private Double rating = 0.0;

    @Builder
    public FlowerShop(String loginId,
                      String password,
                      String email,
                      String ownerName,
                      String shopName,
                      String phoneNumber,
                      String shopImageUrl,
                      String province,
                      String city,
                      String introduction) {
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
