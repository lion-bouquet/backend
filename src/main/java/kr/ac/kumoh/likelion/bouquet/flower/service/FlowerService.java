package kr.ac.kumoh.likelion.bouquet.flower.service;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.exception.NotFoundException;
import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerDetailResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.flower.repository.FlowerRepository;
import kr.ac.kumoh.likelion.bouquet.flower.repository.MatchingColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository flowerRepository;
    private final MatchingColorRepository matchingColorRepository;

    public List<FlowerSummaryResponse> findFlowers(Long colorId, Integer month) {
        // TODO: colorId와 month에 따른 동적 쿼리 로직 구현 (현재는 전체 조회)
        List<Flower> flowers = flowerRepository.findAll();

        return flowers.stream()
                .map(this::mapToFlowerSummaryResponse)
                .collect(Collectors.toList());
    }

    public FlowerDetailResponse findFlowerById(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new NotFoundException("해당 꽃을 찾을 수 없습니다. ID: " + flowerId));

        // 해당 꽃과 매칭되는 색상 정보 조회
        List<ColorResponse> matchingColors = findMatchingColorsByFlowerId(flowerId);

        return mapToFlowerDetailResponse(flower, matchingColors);
    }

    public List<ColorResponse> findMatchingColorsByFlowerId(Long flowerId) {
        if (!flowerRepository.existsById(flowerId)) {
            throw new NotFoundException("해당 꽃을 찾을 수 없습니다. ID: " + flowerId);
        }

        return matchingColorRepository.findByFlower_Id(flowerId).stream()
                .map(matchingColor -> ColorResponse.builder()
                        .id(matchingColor.getColor().getId())
                        .name(matchingColor.getColor().getName())
                        .build())
                .collect(Collectors.toList());
    }


    private FlowerSummaryResponse mapToFlowerSummaryResponse(Flower flower) {
        return FlowerSummaryResponse.builder()
                .id(flower.getId())
                .name(flower.getName())
                .flowerLanguage(flower.getFlowerLanguage())
                .imageUrl(flower.getImageUrl())
                .build();
    }

    private FlowerDetailResponse mapToFlowerDetailResponse(Flower flower, List<ColorResponse> matchingColors) {
        return FlowerDetailResponse.builder()
                .id(flower.getId())
                .name(flower.getName())
                .description(flower.getDescription())
                .flowerLanguage(flower.getFlowerLanguage())
                .isPositive(flower.getIsPositive())
                .matchingColors(matchingColors)
                .seasonStart(flower.getSeasonStart())
                .seasonEnd(flower.getSeasonEnd())
                .size(flower.getSize())
                .imageUrl(flower.getImageUrl())
                .species(flower.getSpecies())
                .origin(flower.getOrigin())
                .scent(flower.getScent())
                .lifespan(flower.getLifespan())
                .build();
    }
}