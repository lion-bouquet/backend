package kr.ac.kumoh.likelion.bouquet.global.oauth.user;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected OAuth2Provider provider;
    protected String accessToken;
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(OAuth2Provider provider, String accessToken, Map<String, Object> attributes) {
        this.provider = provider;
        this.accessToken = accessToken;
        this.attributes = attributes;
    }

    public OAuth2Provider getProvider() {
        return provider;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
