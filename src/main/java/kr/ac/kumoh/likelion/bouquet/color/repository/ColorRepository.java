package kr.ac.kumoh.likelion.bouquet.color.repository;

import kr.ac.kumoh.likelion.bouquet.color.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
