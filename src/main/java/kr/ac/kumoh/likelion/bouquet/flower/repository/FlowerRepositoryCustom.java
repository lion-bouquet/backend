package kr.ac.kumoh.likelion.bouquet.flower.repository;

import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;

import java.util.List;

public interface FlowerRepositoryCustom {
    List<Flower> findFlowersByCriteria(Long colorId, Integer month);
}
