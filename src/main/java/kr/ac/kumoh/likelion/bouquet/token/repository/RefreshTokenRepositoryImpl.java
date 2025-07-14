package kr.ac.kumoh.likelion.bouquet.token.repository;

import kr.ac.kumoh.likelion.bouquet.global.jwt.properties.JwtProperties;
import kr.ac.kumoh.likelion.bouquet.global.jwt.token.refresh.RefreshTokenData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private static final String KEY_PREFIX = "refreshToken";
    private static final String INDEX_PREFIX = "refreshTokenMember";

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;
    private final SetOperations<String, String> setOperations;

    private final JwtProperties jwtProperties;

    public RefreshTokenRepositoryImpl(RedisTemplate<String, String> redisTemplate, JwtProperties jwtProperties) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.setOperations = redisTemplate.opsForSet();

        this.jwtProperties = jwtProperties;
    }

    @Override
    public void save(final RefreshTokenData refreshToken) {
        String tokenKey = getTokenKey(refreshToken.token());
        String indexKey = getIndexKey(refreshToken.userId());

        valueOperations.set(tokenKey, String.valueOf(refreshToken.userId()));
        redisTemplate.expire(tokenKey, jwtProperties.getRefreshTokenExpiredIn(), TimeUnit.SECONDS);

        setOperations.add(indexKey, tokenKey);
        redisTemplate.expire(indexKey, jwtProperties.getRefreshTokenExpiredIn(), TimeUnit.SECONDS);
    }

    @Override
    public Optional<RefreshTokenData> findById(final String refreshToken) {
        String tokenKey = getTokenKey(refreshToken);
        String memberId = valueOperations.get(tokenKey);

        if (Objects.isNull(memberId)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshTokenData(refreshToken, Long.valueOf(memberId)));
    }

    @Override
    public void deleteById(final String refreshToken) {
        String tokenKey = getTokenKey(refreshToken);

        String memberId = valueOperations.get(tokenKey);
        if (Objects.isNull(memberId)) {
            return;
        }
        String indexKey = getIndexKey(Long.valueOf(memberId));

        setOperations.remove(indexKey, tokenKey);
        redisTemplate.delete(tokenKey);

        Long remaining = setOperations.size(indexKey);
        if (remaining == null || remaining == 0) {
            redisTemplate.delete(indexKey);
        }
    }

    @Override
    public void deleteByMemberId(final Long memberId) {
        String indexKey = getIndexKey(memberId);

        Set<String> tokens = setOperations.members(indexKey);
        if (tokens != null) {
            tokens.forEach(redisTemplate::delete);
        }

        redisTemplate.delete(indexKey);
    }

    private String getTokenKey(final String refreshToken) {
        return KEY_PREFIX + ":" + refreshToken;
    }

    private String getIndexKey(final Long memberId) {
        return INDEX_PREFIX + ":" + memberId;
    }
}
