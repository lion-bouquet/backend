package kr.ac.kumoh.likelion.bouquet.flower.service;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerCreateRequest;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerDetailResponse;
import kr.ac.kumoh.likelion.bouquet.flower.dto.FlowerSummaryResponse;
import kr.ac.kumoh.likelion.bouquet.flower.repository.FlowerRepository;
import kr.ac.kumoh.likelion.bouquet.flower.repository.MatchingColorRepository;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.shop.dto.ShopSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository flowerRepository;
    private final MatchingColorRepository matchingColorRepository;

    public List<FlowerSummaryResponse> findFlowers(Long colorId, Integer month) {
        // colorId와 month에 따른 동적 쿼리 로직으로 변경
        List<Flower> flowers = flowerRepository.findFlowersByCriteria(colorId, month);

        return flowers.stream()
                .map(FlowerSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public FlowerDetailResponse findFlowerById(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ServiceException(ErrorCode.FLOWER_NOT_FOUND));

        // 해당 꽃과 매칭되는 색상 정보 조회
        List<ColorResponse> matchingColors = findMatchingColorsByFlowerId(flowerId);

        return FlowerDetailResponse.from(flower, matchingColors);
    }

    public List<FlowerSummaryResponse> findRandomFlowers(int size) {
        return flowerRepository.findRandomFlowers(size).stream()
                .map(FlowerSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public List<ColorResponse> findMatchingColorsByFlowerId(Long flowerId) {
        if (!flowerRepository.existsById(flowerId)) {
            throw new ServiceException(ErrorCode.FLOWER_NOT_FOUND);
        }

        return matchingColorRepository.findByFlower_Id(flowerId).stream()
                .map(matchingColor -> ColorResponse.builder()
                        .id(matchingColor.getColor().getId())
                        .name(matchingColor.getColor().getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void createFlower(FlowerCreateRequest request) {
        Flower flower = request.toEntity();
        flowerRepository.save(flower);
    }
}
