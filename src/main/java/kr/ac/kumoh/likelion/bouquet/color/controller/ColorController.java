package kr.ac.kumoh.likelion.bouquet.color.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.color.dto.ColorResponse;
import kr.ac.kumoh.likelion.bouquet.color.service.ColorService;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.user.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "꽃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/colors")
public class ColorController {

    private final ColorService colorService;

    @Operation(
            summary = "꽃 색상 정보 조회",
            description = "꽃 색상 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ColorResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "꽃 색상 정보 조회에 성공했습니다.",
                    response = ColorResponse[].class
            )
    )
    @GetMapping
    public ResponseEntity<ResponseBody<List<ColorResponse>>> getColors() {
        List<ColorResponse> colors = colorService.findAllColors();
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(colors));
    }
}
