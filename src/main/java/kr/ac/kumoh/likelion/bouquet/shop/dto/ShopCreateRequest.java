package kr.ac.kumoh.likelion.bouquet.shop.dto;

import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import lombok.Getter;

@Getter
public class ShopCreateRequest {
    private String loginId;
    private String password;
    private String email;
    private String ownerName;
    private String shopName;
    private String address;
    private String introduction;
    private String openingHoursWeekdays;
    private String openingHoursWeekend;

    // DTO를 FlowerShop 엔티티로 변환하는 메소드
    public FlowerShop toEntity() {
        return FlowerShop.builder()
                .loginId(this.loginId)
                .password(this.password) // 실제로는 암호화 필요
                .email(this.email)
                .ownerName(this.ownerName)
                .shopName(this.shopName)
                .address(this.address)
                .introduction(this.introduction)
                .rating(0.0) // 초기 평점
                .openingHoursWeekdays(this.openingHoursWeekdays)
                .openingHoursWeekend(this.openingHoursWeekend)
                .build();
    }
}