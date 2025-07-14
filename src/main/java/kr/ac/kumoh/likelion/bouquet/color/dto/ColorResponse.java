package kr.ac.kumoh.likelion.bouquet.color.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorResponse {
    private Long id;
    private String name;
}