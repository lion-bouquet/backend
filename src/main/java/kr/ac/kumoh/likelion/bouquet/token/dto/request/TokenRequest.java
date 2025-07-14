package kr.ac.kumoh.likelion.bouquet.token.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
        @NotBlank(message = "액세스 토큰은 필수 항목입니다.")
        String accessToken,

        @NotBlank(message = "리프레시 토큰은 필수 항목입니다.")
        String refreshToken
) {
}
