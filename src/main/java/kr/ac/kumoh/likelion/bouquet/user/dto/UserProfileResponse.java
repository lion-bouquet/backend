package kr.ac.kumoh.likelion.bouquet.user.dto;

import kr.ac.kumoh.likelion.bouquet.user.domain.User;

public record UserProfileResponse(
        String email,
        String name,
        String profileImage
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(user.getEmail(), user.getName(), user.getProfileImageUrl());
    }
}
