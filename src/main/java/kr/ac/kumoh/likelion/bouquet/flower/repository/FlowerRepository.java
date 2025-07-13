package kr.ac.kumoh.likelion.bouquet.flower.repository;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
