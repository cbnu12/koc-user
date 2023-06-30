package com.koc.user.presentation.api.usermanagement;

import com.koc.user.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String kocId;
    private Long kakaoId;
    private String email;
    private String loginType;
    private String userStatus;

    public static UserResponse fromDto(UserDto dto) {
        return UserResponse.builder()
                .id(dto.getId())
                .kocId(dto.getKocId())
                .kakaoId(dto.getKakaoId())
                .email(dto.getEmail())
                .loginType(dto.getLoginType())
                .userStatus(dto.getUserStatus())
                .build();
    }
}
