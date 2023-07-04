package com.koc.user.presentation.api.auth;


import com.koc.user.domain.kakaouser.KakaoUserDto;
import com.koc.user.domain.kakaouser.KakaoUserService;
import com.koc.user.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    public String getKakaoLoginUrl() {
        return kakaoUserService.getKakaoLoginUrl();
    }

    public String getToken(final String code) {
        String token = kakaoUserService.getKakaoAccessToken(code);
        KakaoUserDto kakaoUser = kakaoUserService.getKakaoUserDtoByToken(token);

        if (Boolean.TRUE.equals(userService.isNotExistUserByKakaoId(kakaoUser.getKakaoId()))) {
            userService.joinAsKakaoUser(kakaoUser.getKakaoId(), kakaoUser.getEmail());
        }

        return token;
    }
}
