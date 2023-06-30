package com.koc.user.sociallogin.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfo {

    private Long id;
    private Map<String, Object> kakaoAccount;

    public KakaoUserInfo(Long id, Map<String, Object> kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }


    public String getEmail() {
        return (String) Optional.ofNullable(kakaoAccount.get("email")).orElse("");
    }
}
