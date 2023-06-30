package com.koc.user.infra.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KakaoUserRepository {
    private final KakaoClient client;

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

    public KakaoTokenResponse findTokenByCode(String code) {
        String kakaoTokenUri = kakaoOauthUri + "/token";
        log.info("kakao request for get token");
        try {
            return client.getToken(new URI(kakaoTokenUri), clientId, redirectUri, code, "authorization_code");
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new KakaoApiException("카카오 사용자 정보를 가져 오는중 오류가 발생했습니다.");
        }
    }

    public KakaoUserInfoResponse findKakaoUserIdByAccessToken(String accessToken) {
        try {
            return client.getKakaoUserInfo(new URI(kapiUserInfoUri), "Bearer " + accessToken);
        } catch (Exception e) {
            log.error("Something error..", e);
            throw new KakaoApiException("카카오 인증 중 오류가 발생했습니다.");
        }
    }
}
