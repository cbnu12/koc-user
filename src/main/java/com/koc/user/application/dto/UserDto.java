package com.koc.user.application.dto;

import com.koc.user.application.enums.LoginType;
import com.koc.user.application.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String kocId;
    private String pw;
    private String refreshToken;
    private Long kakaoId;
    private String email;
    private LoginType loginType;
    private UserStatus userStatus;


}
