package com.koc.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUser {
    private Long kakaoId;
    private String email;

    public KakaoUser(Long kakaoId, String email) {
        this.kakaoId = kakaoId;
        this.email = email;
    }
}
