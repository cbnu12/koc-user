package com.koc.user.entity;

import com.koc.user.domain.KakaoUser;
import com.koc.user.domain.User;
import com.koc.user.enums.LoginType;
import com.koc.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String kocId;
    private String pw;
    private Long kakaoId;
    private String email;
    private LoginType loginType;
    private UserStatus userStatus;

    public User toUser() {
        return User.builder()
                .id(id)
                .kocId(kocId)
                .pw(pw)
                .kakaoUser(new KakaoUser(kakaoId, email))
                .loginType(loginType)
                .userStatus(userStatus)
                .build();
    }
}
