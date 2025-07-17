package kr.ac.kumoh.likelion.bouquet.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.user.dto.UserProfileResponse;
import kr.ac.kumoh.likelion.bouquet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "사용자")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Tag(name = "사용자")
    @Operation(
            summary = "사용자 정보 조회",
            description = "사용자 정보를 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "사용자 정보를 조회에 성공했습니다.",
                    response = UserProfileResponse.class
            ),
            errors = {
                    @SwaggerApiFailedResponse(ErrorCode.USER_NOT_FOUND)
            }
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseBody<UserProfileResponse>> getUserProfile(@CurrentUserId Long userId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(userService.getUserProfile(userId)));
    }
}
