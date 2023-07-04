package com.koc.user.infra.kakao;

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
public class KakaoUserInfoResponse {

    private Long id;
    private Map<String, Object> kakaoAccount;

    public KakaoUserInfoResponse(Long id, Map<String, Object> kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }


    public String getEmail() {
        return (String) Optional.ofNullable(kakaoAccount.get("email")).orElse("");
    }
}
