package kr.ac.kumoh.likelion.bouquet.flower.repository;

import kr.ac.kumoh.likelion.bouquet.flower.domain.MatchingColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingColorRepository extends JpaRepository<MatchingColor, Long> {
    List<MatchingColor> findByFlower_Id(Long flowerId);
}