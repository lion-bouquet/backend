package kr.ac.kumoh.likelion.bouquet.global.oauth.service;

import kr.ac.kumoh.likelion.bouquet.global.oauth.exception.OAuth2AuthenticationProcessingException;
import kr.ac.kumoh.likelion.bouquet.global.oauth.user.OAuth2UserInfo;
import kr.ac.kumoh.likelion.bouquet.global.oauth.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processOauth2User(userRequest, oauth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOauth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        OAuth2UserInfo oauth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, accessToken, oauth2User.getAttributes());

        if(!StringUtils.hasText(oauth2UserInfo.getId())) {
            throw new OAuth2AuthenticationProcessingException("해당 식별자는 OAuth 2.0 제공자에서 찾을 수 없습니다.");
        }

        return new OAuth2UserPrincipal(oauth2UserInfo);
    }
}
