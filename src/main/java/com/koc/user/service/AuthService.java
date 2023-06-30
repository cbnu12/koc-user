package com.koc.user.service;


import com.koc.user.domain.KakaoUser;
import com.koc.user.domain.User;
import com.koc.user.enums.LoginType;
import com.koc.user.enums.UserStatus;
import com.koc.user.service.domainService.UserDomainService;
import com.koc.user.sociallogin.kakao.KakaoClient;
import com.koc.user.sociallogin.kakao.KakaoToken;
import com.koc.user.sociallogin.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
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

    public String getToken(final String code) {
        KakaoToken kakaoToken = getTokenFromKakao(code);
        log.debug("kakaoToken = {}", kakaoToken);
        KakaoUserInfo info = getKakaoUserInfo(kakaoToken.getAccessToken());
        log.debug("kakaoUserInfo = {}", info);
        Optional<User> user = userDomainService.findByKakaoId(info.getId());

        if (user.isEmpty()) {
            kakaoJoin(info);
        }

        return kakaoToken.getAccessToken();
    }

    private KakaoToken getTokenFromKakao(final String code) {
        String kakaoTokenUri = kakaoOauthUri + "/token";
        try {
            return client.getToken(new URI(kakaoTokenUri), clientId, redirectUri, code, "authorization_code");
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new RuntimeException("카카오 사용자 정보를 가져 오는중 오류가 발생했습니다.");
        }
    }

    private KakaoUserInfo getKakaoUserInfo(String token) {
        try {
            return client.getKakaoUserInfo(new URI(kapiUserInfoUri), "Bearer " + token);
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new RuntimeException("카카오 인증 중 오류가 발생했습니다.");
        }
    }

    private void kakaoJoin(KakaoUserInfo kakaoUserInfo) {
        KakaoUser kakaoUser = KakaoUser.builder().
                kakaoId(kakaoUserInfo.getId())
                .email(kakaoUserInfo.getEmail())
                .build();

        User user = User.builder()
                .kakaoUser(kakaoUser)
                .loginType(LoginType.KAKAO)
                .userStatus(UserStatus.ACTIVE)
                .build();

        userDomainService.save(user);
    }

}
