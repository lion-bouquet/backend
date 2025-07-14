package kr.ac.kumoh.likelion.bouquet.global.jwt.token.access;

public record AccessTokenData(
        String token,
        int expiredIn
){
}
