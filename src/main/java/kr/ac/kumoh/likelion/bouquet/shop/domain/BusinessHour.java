package kr.ac.kumoh.likelion.bouquet.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {
    @Column(name = "open_time", nullable = false)
    private LocalTime open;

    @Column(name = "close_time", nullable = false)
    private LocalTime close;

    @Builder
    public BusinessHour(LocalTime open, LocalTime close) {
        this.open = open;
        this.close = close;
    }
}
