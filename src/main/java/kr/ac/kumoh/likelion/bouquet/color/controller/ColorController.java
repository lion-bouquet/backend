package kr.ac.kumoh.likelion.bouquet.color.controller;

import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.color.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colors")
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorResponse>> getColors() {
        List<ColorResponse> colors = colorService.findAllColors();
        return ResponseEntity.ok(colors);
    }
}