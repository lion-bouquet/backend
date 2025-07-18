package kr.ac.kumoh.likelion.bouquet.review.repository;

import kr.ac.kumoh.likelion.bouquet.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * 특정 꽃집의 리뷰 개수를 반환합니다.
     * @param flowerShopId 꽃집 ID
     * @return 리뷰 개수
     */
    long countByFlowerShop(Long flowerShopId);
}
