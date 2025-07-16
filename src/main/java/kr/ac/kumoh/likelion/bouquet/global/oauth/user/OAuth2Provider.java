package kr.ac.kumoh.likelion.bouquet.global.oauth.user;

public enum OAuth2Provider {
    GOOGLE("google");

    private final String registrationId;

    OAuth2Provider(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }
}
