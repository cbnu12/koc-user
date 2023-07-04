package com.koc.user.infra.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String kocId;
    private String pw;
    private Long kakaoId;
    private String email;
    private String loginType;
    private String userStatus;

    @Builder
    public UserEntity(Long id, String kocId, String pw, Long kakaoId, String email, String loginType, String userStatus) {
        this.id = id;
        this.kocId = kocId;
        this.pw = pw;
        this.kakaoId = kakaoId;
        this.email = email;
        this.loginType = loginType;
        this.userStatus = userStatus;
    }
}
