package kr.ac.kumoh.likelion.bouquet.order.repository;

import kr.ac.kumoh.likelion.bouquet.order.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
