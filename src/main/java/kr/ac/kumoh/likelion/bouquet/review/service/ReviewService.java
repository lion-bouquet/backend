package kr.ac.kumoh.likelion.bouquet.review.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.review.domain.Review;
import kr.ac.kumoh.likelion.bouquet.review.dto.ReviewCreateRequest;
import kr.ac.kumoh.likelion.bouquet.review.dto.ReviewResponse;
import kr.ac.kumoh.likelion.bouquet.review.repository.ReviewRepository;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import kr.ac.kumoh.likelion.bouquet.user.domain.User;
import kr.ac.kumoh.likelion.bouquet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponse createReview(Long userId, Long shopId, ReviewCreateRequest reviewCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        Review review = Review.builder()
                .shop(shop)
                .user(user)
                .stars(reviewCreateRequest.stars())
                .content(reviewCreateRequest.content())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        return ReviewResponse.from(review);
    }

    public List<ReviewResponse> getReviews(Long shopId) {
        FlowerShop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        List<Review> reviews = reviewRepository.findByShop(shop);

        return reviews.stream()
                .map(ReviewResponse::from)
                .toList();
    }
}
