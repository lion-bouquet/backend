package kr.ac.kumoh.likelion.bouquet.global.jwt.token.access;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.ac.kumoh.likelion.bouquet.global.jwt.JwtClaims;
import kr.ac.kumoh.likelion.bouquet.global.jwt.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class AccessTokenProvider {

    private static final String USER_ID = "USER_ID";
    private static final long MILLI_SECOND = 1000L;

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public AccessTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSalt().getBytes(StandardCharsets.UTF_8));
    }

    public AccessTokenData createToken(JwtClaims jwtClaims) {
        Date now = new Date(System.currentTimeMillis());
        Date expired = new Date(now.getTime() + (long) jwtProperties.getAccessTokenExpiredIn() * MILLI_SECOND);

        return new AccessTokenData(Jwts.builder()
                .claims(generateClaims(jwtClaims))
                .issuedAt(now)
                .expiration(expired)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact(), jwtProperties.getAccessTokenExpiredIn());
    }

    private Map<String, Object> generateClaims(JwtClaims jwtClaims) {
        return Map.of(USER_ID, jwtClaims.userId());
    }

    public JwtClaims parseToken(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        return convertJwtClaims(claims);
    }

    public Optional<JwtClaims> getClaims(String accessToken) {
        try {
            return Optional.of(parseToken(accessToken));
        } catch (ExpiredJwtException e) {
            return Optional.of(convertJwtClaims(e.getClaims()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private JwtClaims convertJwtClaims(Claims claims) {
        return new JwtClaims(claims.get(USER_ID, Long.class));
    }

}
