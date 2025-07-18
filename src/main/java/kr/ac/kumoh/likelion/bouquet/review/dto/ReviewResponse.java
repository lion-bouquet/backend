package kr.ac.kumoh.likelion.bouquet.review.dto;

import kr.ac.kumoh.likelion.bouquet.review.domain.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long userId,
        String name,
        String profileImageUrl,
        LocalDateTime createdAt,
        Float stars,
        String content
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review.getUser().getId(),
                review.getUser().getName(),
                review.getUser().getProfileImageUrl(),
                review.getCreatedAt(),
                review.getStars(),
                review.getContent());
    }
}
