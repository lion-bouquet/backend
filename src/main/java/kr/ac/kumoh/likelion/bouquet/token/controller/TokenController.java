package kr.ac.kumoh.likelion.bouquet.token.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.likelion.bouquet.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenCodeRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.response.TokenResponse;
import kr.ac.kumoh.likelion.bouquet.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @Operation(
            summary = "토큰 발급",
            description = "액세스 토큰과 리프레시 토큰을 발급합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "토큰 발급에 성공했습니다.",
                    response = TokenResponse.class
            ),
            errors = {
                    @SwaggerApiFailedResponse(ErrorCode.JWT_EXPIRED),
                    @SwaggerApiFailedResponse(ErrorCode.USER_NOT_FOUND)
            }
    )
    @PostMapping("/token")
    public ResponseEntity<ResponseBody<TokenResponse>> getToken(@RequestBody @Valid TokenCodeRequest tokenRequest) {
        TokenResponse newToken = tokenService.generateToken(tokenRequest);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(newToken));
    }

    @Operation(
            summary = "토큰 갱신",
            description = "액세스 토큰과 리프레시 토큰을 갱신합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "토큰 갱신에 성공했습니다.",
                    response = TokenResponse.class
            ),
            errors = {
                    @SwaggerApiFailedResponse(ErrorCode.JWT_EXPIRED),
                    @SwaggerApiFailedResponse(ErrorCode.JWT_INVALID)
            }
    )
    @PostMapping("/refresh")
    public ResponseEntity<ResponseBody<TokenResponse>> refresh(@RequestBody @Valid TokenRequest tokenRequest) {
        TokenResponse newToken = tokenService.refresh(tokenRequest);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(newToken));
    }

    @Operation(
            summary = "로그아웃",
            description = "리프레시 토큰을 삭제합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "토큰 삭제에 성공했습니다."
            ),
            errors = {
                    @SwaggerApiFailedResponse(ErrorCode.JWT_EXPIRED),
                    @SwaggerApiFailedResponse(ErrorCode.JWT_INVALID)
            }
    )
    @DeleteMapping("/sign-out")
    public ResponseEntity<ResponseBody<Void>> signOut(@RequestBody @Valid TokenRequest tokenRequest) {
        tokenService.signOut(tokenRequest);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse());
    }
}
