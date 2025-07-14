package kr.ac.kumoh.likelion.bouquet.token.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseBody;
import kr.ac.kumoh.likelion.bouquet.global.base.dto.ResponseUtils;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenCodeRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.request.TokenRequest;
import kr.ac.kumoh.likelion.bouquet.token.dto.response.TokenResponse;
import kr.ac.kumoh.likelion.bouquet.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<ResponseBody<TokenResponse>> refresh(@RequestBody @Valid TokenCodeRequest tokenRequest) {
        TokenResponse newToken = tokenService.generateToken(tokenRequest);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(newToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseBody<TokenResponse>> refresh(@RequestBody @Valid TokenRequest tokenRequest) {
        TokenResponse newToken = tokenService.refresh(tokenRequest);
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(newToken));
    }
}
