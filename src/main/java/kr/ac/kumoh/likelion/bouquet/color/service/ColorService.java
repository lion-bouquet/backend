package kr.ac.kumoh.likelion.bouquet.color.service;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.color.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public List<ColorResponse> findAllColors() {
        return colorRepository.findAll().stream()
                .map(color -> ColorResponse.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .build())
                .collect(Collectors.toList());
    }
}