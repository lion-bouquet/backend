package kr.ac.kumoh.likelion.bouquet.global.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.backend.jwt")
public class JwtProperties {
    private String salt;
    private int accessTokenExpiredIn;
    private int refreshTokenExpiredIn;
}
