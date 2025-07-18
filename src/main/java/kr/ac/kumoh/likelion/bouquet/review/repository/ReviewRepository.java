package kr.ac.kumoh.likelion.bouquet.review.repository;

import kr.ac.kumoh.likelion.bouquet.review.domain.Review;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByShop(FlowerShop shop);

    int countByShop(FlowerShop shop);
}
