package kr.ac.kumoh.likelion.bouquet.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.ac.kumoh.likelion.bouquet.global.oauth.user.OAuth2Provider;
import kr.ac.kumoh.likelion.bouquet.order.domain.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    private String providerId;

    private String name;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(OAuth2Provider provider, String providerId, String name, String email) {
        this.provider = provider;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
    }
}
