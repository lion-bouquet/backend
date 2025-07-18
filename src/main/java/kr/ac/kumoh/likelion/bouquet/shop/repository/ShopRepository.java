package kr.ac.kumoh.likelion.bouquet.shop.repository;

import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<FlowerShop, Long> {
    List<FlowerShop> findByShopNameContaining(String name);

    @Query(value = "SELECT * FROM flower_shop ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<FlowerShop> findRandomShops(@Param("size") int size);
}
