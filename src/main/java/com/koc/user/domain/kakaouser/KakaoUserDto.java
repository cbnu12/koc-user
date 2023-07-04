package com.koc.user.domain.kakaouser;

import com.koc.user.infra.kakao.KakaoUserInfoResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUserDto {
    private Long kakaoId;
    private String email;

    public KakaoUserDto(Long kakaoId, String email) {
        this.kakaoId = kakaoId;
        this.email = email;
    }

    public static KakaoUserDto from(KakaoUserInfoResponse infoResponse) {
        return KakaoUserDto.builder()
                .kakaoId(infoResponse.getId())
                .email(infoResponse.getEmail())
                .build();
    }
}
