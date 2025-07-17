package kr.ac.kumoh.likelion.bouquet.user.controller;

import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.global.jwt.annotation.CurrentUserId;
import kr.ac.kumoh.likelion.bouquet.user.dto.UserProfileResponse;
import kr.ac.kumoh.likelion.bouquet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ResponseBody<UserProfileResponse>> getUserProfile(@CurrentUserId Long userId) {
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(userService.getUserProfile(userId)));
    }
}
