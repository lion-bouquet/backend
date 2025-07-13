package kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh;

public record RefreshTokenData(
        String token,
        Long userId
) {
}
