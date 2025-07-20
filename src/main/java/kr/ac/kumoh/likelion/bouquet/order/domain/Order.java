package kr.ac.kumoh.likelion.bouquet.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    // 주요 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 꽃집
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private FlowerShop shop;

    // 주문 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    // 주문 일시
    @Column(name = "order_date", nullable = false)
    @CreatedDate
    private LocalDateTime orderDate;

    // 픽업 가능 시각
    @Column(name = "avail_date")
    private LocalDateTime availDate;

    @Column(name = "phone_number")
    private String phoneNumber;
    
    // 주문 요청
    @Column(name = "content", length = 500)
    private String content;

    // 주문 가격
    @Column(name = "total_price", nullable = false)
    private Long totalPrice = 0L;

    // 주문 상세
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Builder
    public Order(User user, FlowerShop shop, OrderStatus status, LocalDateTime orderDate, String phoneNumber, String content) {
        this.user = user;
        this.shop = shop;
        this.status = status;
        this.orderDate = orderDate;
        this.phoneNumber = phoneNumber;
        this.content = content;
    }

    public void accept(LocalDateTime availDate) {
        this.status = OrderStatus.PENDING;
        this.availDate = availDate;
    }

    public boolean isCancellable() {
        return this.status == OrderStatus.WAIT;
    }

    public void cancel() {
        this.status = OrderStatus.CANCEL;
    }

    public void plusTotalPrice(long price) {
        this.totalPrice += price;
    }
}
