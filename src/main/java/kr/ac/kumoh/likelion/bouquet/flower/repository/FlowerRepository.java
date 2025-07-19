package kr.ac.kumoh.likelion.bouquet.flower.repository;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long>, FlowerRepositoryCustom {
    @Query(value = "SELECT * FROM flowers ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Flower> findRandomFlowers(@Param("size") int size);
}
