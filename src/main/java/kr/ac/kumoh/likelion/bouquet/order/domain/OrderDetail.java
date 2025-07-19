package kr.ac.kumoh.likelion.bouquet.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.ac.kumoh.likelion.bouquet.stock.domain.Stock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // 판매 상품
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    // 단가
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice = 0;

    // 개수
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Builder
    public OrderDetail(Order order, Stock stock, Integer unitPrice, Integer quantity) {
        this.order = order;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
