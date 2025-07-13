package kr.ac.kumoh.likelion.bouquet.token.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenCodeRequest(
        @NotBlank(message = "토큰 코드는 필수 항목입니다.")
        String tokenCode
) {
}
