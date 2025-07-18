package kr.ac.kumoh.likelion.bouquet.token.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.global.jwt.JwtClaims;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.access.AccessTokenData;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh.RefreshTokenData;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh.RefreshTokenProvider;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenCodeRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.response.TokenResponse;
import kr.ac.kumoh.likelion.bouquet.token.repository.RefreshTokenRepository;
import kr.ac.kumoh.likelion.bouquet.token.repository.TokenCodeRepository;
import kr.ac.kumoh.likelion.bouquet.user.domain.User;
import kr.ac.kumoh.likelion.bouquet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;

    private final TokenCodeRepository tokenCodeRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public TokenResponse generateToken(TokenCodeRequest tokenCode) {
        // 요청으로 온 토큰 코드를 Redis에서 가져옵니다.
        Long memberId = tokenCodeRepository.findById(tokenCode.tokenCode())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_EXPIRED));
        tokenCodeRepository.deleteById(tokenCode.tokenCode());

        // 사용자 정보를 가져와 JWT 클레임 생성
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        JwtClaims claims = JwtClaims.create(user);

        // 새로운 액세스 토큰과 리프레시 토큰을 발급해 반환합니다.
        AccessTokenData newAccessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData newRefreshToken = refreshTokenProvider.createToken(claims);
        refreshTokenRepository.save(new RefreshTokenData(newRefreshToken.token(), claims.userId()));

        return new TokenResponse(newAccessToken.token(), newRefreshToken.token());
    }

    public TokenResponse refresh(TokenRequest token) {
        // 만료된 액세스 토큰에서 사용자 정보를 추출합니다.
        JwtClaims claims = accessTokenProvider.getClaims(token.accessToken())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_INVALID));

        // 요청으로 온 리프레시 토큰을 Redis에서 가져옵니다.
        RefreshTokenData savedRefreshToken = refreshTokenRepository.findById(token.refreshToken())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_EXPIRED));

        // 요청한 사용자 정보와 Redis에 저장된 리프레시 토큰의 사용자 정보가 일치하는지 확인합니다.
        if (!claims.userId().equals(savedRefreshToken.userId())) {
            throw new ServiceException(ErrorCode.JWT_INVALID);
        }

        // 새로운 액세스 토큰과 리프레시 토큰을 발급해 반환합니다.
        AccessTokenData newAccessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData newRefreshToken = refreshTokenProvider.createToken(claims);
        refreshTokenRepository.save(new RefreshTokenData(newRefreshToken.token(), claims.userId()));

        return new TokenResponse(newAccessToken.token(), newRefreshToken.token());
    }

    public void signOut(TokenRequest token) {
        // 만료된 액세스 토큰에서 사용자 정보를 추출합니다.
        JwtClaims claims = accessTokenProvider.getClaims(token.accessToken())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_INVALID));

        // 요청으로 온 리프레시 토큰을 Redis에서 가져옵니다.
        RefreshTokenData savedRefreshToken = refreshTokenRepository.findById(token.refreshToken())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_EXPIRED));

        // 요청한 사용자 정보와 Redis에 저장된 리프레시 토큰의 사용자 정보가 일치하는지 확인합니다.
        if (!claims.userId().equals(savedRefreshToken.userId())) {
            throw new ServiceException(ErrorCode.JWT_INVALID);
        }

        refreshTokenRepository.deleteById(token.refreshToken());
    }
}
