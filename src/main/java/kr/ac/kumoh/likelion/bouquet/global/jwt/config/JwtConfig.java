package kr.ac.kumoh.likelion.bouquet.global.jwt.config;

import kr.ac.kumoh.likelion.bouquet.global.jwt.authentication.JwtAuthenticationProvider;
import kr.ac.kumoh.likelion.bouquet.global.jwt.properties.JwtProperties;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh.RefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ JwtProperties.class })
public class JwtConfig {
    @Bean
    public AccessTokenProvider accessTokenProvider(JwtProperties jwtProperties) {
        return new AccessTokenProvider(jwtProperties);
    }

    @Bean
    public RefreshTokenProvider refreshTokenProvider() {
        return new RefreshTokenProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(JwtAuthenticationProvider jwtAuthenticationProvider) {
        return new ProviderManager(jwtAuthenticationProvider);
    }
}
