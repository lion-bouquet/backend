package kr.ac.kumoh.likelion.bouquet.global.oauth.user;

import kr.ac.kumoh.likelion.bouquet.global.oauth.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, String accessToken, Map<String,Object> attributes) {
        if (OAuth2Provider.GOOGLE.getRegistrationId().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(accessToken, attributes);
        }

        throw new OAuth2AuthenticationProcessingException("해당 OAuth 2.0 Provider (" + registrationId + ")는 지원하지 않습니다.");
    }
}
