package kr.ac.kumoh.likelion.bouquet.global.oauth.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.likelion.bouquet.global.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import kr.ac.kumoh.likelion.bouquet.global.oauth.service.OAuth2UserPrincipal;
import kr.ac.kumoh.likelion.bouquet.global.oauth.user.OAuth2Provider;
import kr.ac.kumoh.likelion.bouquet.global.oauth.user.OAuth2UserInfo;
import kr.ac.kumoh.likelion.bouquet.global.utils.CookieUtils;
import kr.ac.kumoh.likelion.bouquet.token.repository.TokenCodeRepository;
import kr.ac.kumoh.likelion.bouquet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import kr.ac.kumoh.likelion.bouquet.user.domain.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String MODE_PARAM_COOKIE_NAME = "mode";
    private static final String LOGIN_MODE = "login";
    private static final String LOGIN_FAILED_ERROR_MESSAGE = "login_failed";

    private final String authorizedRedirectUri;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final UserRepository userRepository;
    private final TokenCodeRepository tokenCodeRepository;

    public OAuth2AuthenticationSuccessHandler(@Value("${app.frontend.authorized-redirect-uri}") String authorizedRedirectUri,
                                              HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
                                              UserRepository userRepository,
                                              TokenCodeRepository tokenCodeRepository) {
        this.authorizedRedirectUri = authorizedRedirectUri;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.userRepository = userRepository;
        this.tokenCodeRepository = tokenCodeRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl = this.determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            return;
        }

        this.clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = getTargetUrl(request);
        String mode = getMode(request);

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return handleError(targetUrl, LOGIN_FAILED_ERROR_MESSAGE);
        }

        return switch (mode) {
            case LOGIN_MODE -> handleLogin(principal, targetUrl);
            default -> handleError(targetUrl, LOGIN_FAILED_ERROR_MESSAGE);
        };
    }

    private String getTargetUrl(HttpServletRequest request) {
        return authorizedRedirectUri;
    }

    private String getMode(HttpServletRequest request) {
        return CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(LOGIN_MODE);
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    private String handleLogin(OAuth2UserPrincipal principal, String targetUrl) {
        OAuth2UserInfo userInfo = principal.getUserInfo();
        String providerId = userInfo.getId();
        OAuth2Provider provider = userInfo.getProvider();

        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> createAndSaveNewUser(userInfo));

        String tokenCode = UUID.randomUUID().toString();
        tokenCodeRepository.save(tokenCode, user.getId());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token-code", tokenCode)
                .build().toUriString();
    }

    private User createAndSaveNewUser(OAuth2UserInfo userInfo) {
        User user = User
                .builder()
                .provider(userInfo.getProvider())
                .providerId(userInfo.getId())
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .build();

        return userRepository.save(user);
    }

    private String handleError(String targetUrl, String errorMessage) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", errorMessage)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
