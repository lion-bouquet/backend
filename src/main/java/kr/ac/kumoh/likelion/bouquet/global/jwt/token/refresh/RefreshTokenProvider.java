package kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh;

import kr.ac.kumoh.likelion.bouquet.global.jwt.JwtClaims;
import kr.ac.kumoh.likelion.bouquet.global.jwt.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RefreshTokenProvider {
    public RefreshTokenData createToken(JwtClaims claims) {
        return new RefreshTokenData(UUID.randomUUID().toString(), claims.userId());
    }
}
