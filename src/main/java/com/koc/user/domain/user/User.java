package com.koc.user.domain.user;

import com.koc.user.domain.kakaouser.KakaoUserDto;
import com.koc.user.infra.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String kocId;
    private String pw;
    private Long kakaoUserId;
    private String email;
    private LoginType loginType;
    private UserStatus userStatus;

    public LoginType isKakaoLogin() {
        return LoginType.KAKAO;
    }

    public boolean isActiveUser() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    public void withdraw() {
        this.userStatus = UserStatus.INACTIVE;
    }

    public static User createKakaUser(Long kakaoUserId, String email) {
        return User.builder()
                .kakaoUserId(kakaoUserId)
                .email(email)
                .loginType(LoginType.KAKAO)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public static User fromEntity(UserEntity entity) {
            return User.builder()
                    .id(entity.getId())
                    .kocId(entity.getKocId())
                    .pw(entity.getPw())
                    .kakaoUserId(entity.getKakaoId())
                    .email(entity.getEmail())
                    .loginType(LoginType.valueOf(entity.getLoginType()))
                    .userStatus(UserStatus.valueOf(entity.getUserStatus()))
                    .build();

    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .kocId(kocId)
                .pw(pw)
                .kakaoId(kakaoUserId)
                .email(email)
                .loginType(loginType.name())
                .userStatus(userStatus.name())
                .build();
    }

    public UserDto toDto() {
        return UserDto
                .builder()
                .id(id)
                .kocId(kocId)
                .pw(pw)
                .kakaoId(kakaoUserId)
                .email(email)
                .loginType(loginType.name())
                .userStatus(userStatus.name())
                .build();
    }
}
