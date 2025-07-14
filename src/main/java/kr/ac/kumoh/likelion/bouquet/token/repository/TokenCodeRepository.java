package kr.ac.kumoh.likelion.bouquet.token.repository;

import java.util.Optional;

public interface TokenCodeRepository {
    void save(final String tokenCode, Long memberId);

    Optional<Long> findById(final String tokenCode);

    void deleteById(final String tokenCode);
}
