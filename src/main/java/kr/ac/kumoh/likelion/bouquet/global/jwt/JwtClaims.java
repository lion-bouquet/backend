package kr.ac.kumoh.likelion.bouquet.global.jwt;

import kr.ac.kumoh.likelion.bouquet.user.domain.User;

public record JwtClaims(
        Long userId
) {
    public static JwtClaims create(User user) {
        return new JwtClaims(user.getId());
    }
}
