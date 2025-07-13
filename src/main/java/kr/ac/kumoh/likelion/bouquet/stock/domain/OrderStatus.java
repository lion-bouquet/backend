package kr.ac.kumoh.likelion.bouquet.stock.domain;

public enum OrderStatus {
    WAIT,           // 주문 대기
    PENDING,        // 주문 접수
    PICKUP,         // 수령 대기
    COMPLETE,       // 수령 완료
    CANCEL          // 주문 취소
    ;
}
