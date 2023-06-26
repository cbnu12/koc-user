package com.koc.user.domain;

import com.koc.user.application.dto.UserDto;
import com.koc.user.application.enums.LoginType;
import com.koc.user.application.enums.UserStatus;
import com.koc.user.infrastructure.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String kocId;
    private String pw;
    private KakaoUser kakaoUser;
    private LoginType loginType;
    private UserStatus userStatus;

    public LoginType isKakaoLogin() {
        return LoginType.KAKAO;
    }

    public boolean isNomalUser() {
        return this.userStatus == UserStatus.NOMAL;
    }

    public void withdraw() {
        this.userStatus = UserStatus.WITHDRAW;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .kocId(kocId)
                .pw(pw)
                .kakaoId(kakaoUser.getKakaoId())
                .email(kakaoUser.getEmail())
                .loginType(loginType)
                .userStatus(userStatus)
                .build();
    }

    public UserDto toDto() {
        return UserDto
                .builder()
                .id(id)
                .kocId(kocId)
                .pw(pw)
                .kakaoId(kakaoUser.getKakaoId())
                .email(kakaoUser.getEmail())
                .loginType(loginType)
                .userStatus(userStatus)
                .build();
    }
}
