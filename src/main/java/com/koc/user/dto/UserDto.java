package com.koc.user.dto;

import com.koc.user.enums.LoginType;
import com.koc.user.enums.UserStatus;
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
    private Long kakaoId;
    private String email;
    private LoginType loginType;
    private UserStatus userStatus;


}
