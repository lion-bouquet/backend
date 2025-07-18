package kr.ac.kumoh.likelion.bouquet.review.dto;

public record ReviewCreateRequest(
        Float stars,
        String content
) {
}
