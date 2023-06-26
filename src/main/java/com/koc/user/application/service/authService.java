package com.koc.user.application.service;


import com.koc.user.application.enums.LoginType;
import com.koc.user.application.enums.UserStatus;
import com.koc.user.application.service.domainService.UserDomainService;
import com.koc.user.application.sociallogin.kakao.KakaoClient;
import com.koc.user.application.sociallogin.kakao.KakaoToken;
import com.koc.user.application.sociallogin.kakao.KakaoUserInfo;
import com.koc.user.domain.KakaoUser;
import com.koc.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class authService {
    private final KakaoClient client;
    private final UserDomainService userDomainService;
    @Value("${social-login.kakao.oauth_uri}")
    private String kakaoOauthUri;
    @Value("${social-login.kakao.client_id}")
    private String clientId;
    @Value("${social-login.kakao.redirect_uri}")
    private String redirectUri;
    @Value("${social-login.kakao.kapi_user_info_uri}")
    private String kapiUserInfoUri;
    @Value("${social-login.kakao.javascript_key}")
    private String javascriptKey;

    public String getKakaoLoginUrl() {
        return kakaoOauthUri + "/authorize?client_id=" + javascriptKey + "&redirect_uri=" + redirectUri + "&response_type=code";
    }


    public String login(final String code) {
        KakaoToken kakaoToken = getToken(code);
        log.debug(kakaoToken.toString());
        KakaoUserInfo info = getKakaoUserInfo(kakaoToken.getAccessToken());
        log.debug(info.toString());
        Optional<User> user = userDomainService.findByKakaoId(info.getId());
        if (user.isEmpty()) {
            Optional.of(kakaoJoin(info));
        }
        return kakaoToken.getAccessToken();
    }

    public KakaoToken getToken(final String code) {
        String kakaoTokenUri = kakaoOauthUri + "/token";
        try {
            return client.getToken(new URI(kakaoTokenUri), clientId, redirectUri, code, "authorization_code");
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new RuntimeException("카카오 사용자 정보를 가져 오는중 오류가 발생했습니다.");
        }
    }

    public KakaoUserInfo getKakaoUserInfo(String token) {
        try {
            return client.getKakaoUserInfo(new URI(kapiUserInfoUri), "Bearer " + token);
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new RuntimeException("카카오 인증중 오류가 발생했습니다.");
        }
    }

    public User kakaoJoin(KakaoUserInfo kakaoUserInfo) {
        KakaoUser kakaoUser = KakaoUser.builder().
                kakaoId(kakaoUserInfo.getId())
                .email(kakaoUserInfo.getEmail())
                .build();

        User user = User.builder()
                .kakaoUser(kakaoUser)
                .loginType(LoginType.KAKAO)
                .userStatus(UserStatus.ACTIVE)
                .build();

        return userDomainService.save(user);
    }

}
