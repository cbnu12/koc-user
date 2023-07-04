package com.koc.user.domain.kakaouser;

import com.koc.user.infra.kakao.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final KakaoUserRepository kakaoUserRepository;

    public String getKakaoLoginUrl() {
        return kakaoUserRepository.getKakaoLoginUrl();
    }

    public String getKakaoAccessToken(String code) {
        var token = kakaoUserRepository.findTokenByCode(code);
        return token.getAccessToken();
    }

    public KakaoUserDto getKakaoUserDtoByToken(String token) {
        var kakaoUser = kakaoUserRepository.findKakaoUserIdByAccessToken(token);
        return KakaoUserDto.from(kakaoUser);
    }
}
