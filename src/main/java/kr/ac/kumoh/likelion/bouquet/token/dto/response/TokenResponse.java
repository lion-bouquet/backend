package kr.ac.kumoh.likelion.bouquet.token.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
