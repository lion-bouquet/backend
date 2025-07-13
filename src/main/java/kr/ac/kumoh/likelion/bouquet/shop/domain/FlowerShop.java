package kr.ac.kumoh.likelion.bouquet.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Column(nullable = false)
    private String address;

    @Lob
    private String introduction;

    private Double rating = 0.0;

    // 영업 시간 필드
    private String openingHoursWeekdays; // 예: "09:00 - 18:00"
    private String openingHoursWeekend;  // 예: "10:00 - 17:00"
}