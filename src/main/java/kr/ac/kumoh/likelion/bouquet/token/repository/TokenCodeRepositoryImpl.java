package kr.ac.kumoh.likelion.bouquet.token.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenCodeRepositoryImpl implements TokenCodeRepository {
    private static final String KEY_PREFIX = "tokenCode";
    private static final int TOKEN_CODE_EXPIRED_IN = 600;

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public TokenCodeRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(String tokenCode, Long memberId) {
        String key = getKey(tokenCode);
        valueOperations.set(key, memberId.toString());
        redisTemplate.expire(key, TOKEN_CODE_EXPIRED_IN, TimeUnit.SECONDS);
    }

    @Override
    public Optional<Long> findById(String tokenCode) {
        String key = getKey(tokenCode);

        String userId = valueOperations.get(key);
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(userId));
    }

    @Override
    public void deleteById(String tokenCode) {
        String key = getKey(tokenCode);
        redisTemplate.delete(key);
    }

    private String getKey(final String tokenCode) {
        return KEY_PREFIX + ":" + tokenCode;
    }
}
