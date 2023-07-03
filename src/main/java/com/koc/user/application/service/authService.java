package com.koc.user.application.service;


import com.koc.user.application.enums.LoginType;
import com.koc.user.application.enums.UserStatus;
import com.koc.user.application.jwt.JwtProvider;
import com.koc.user.application.jwt.TokenCheckResponse;
import com.koc.user.application.jwt.TokenDataResponse;
import com.koc.user.application.jwt.TokenResponse;
import com.koc.user.application.service.domainService.UserDomainService;
import com.koc.user.application.service.domainService.UserTokenDomainService;
import com.koc.user.application.sociallogin.kakao.KakaoClient;
import com.koc.user.application.sociallogin.kakao.KakaoToken;
import com.koc.user.application.sociallogin.kakao.KakaoUserInfo;
import com.koc.user.domain.KakaoUser;
import com.koc.user.domain.User;
import com.koc.user.domain.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private final UserTokenDomainService userTokenDomainService;
    private static final int accessTokenValidTime = 30;
    private static final int refreshTokenValidTime = 300;
    private final JwtProvider jwtProvider;
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


    public TokenResponse login(final String code) {
        KakaoToken kakaoToken = getToken(code);
        log.debug(kakaoToken.toString());
        KakaoUserInfo info = getKakaoUserInfo(kakaoToken.getAccessToken());
        log.debug(info.toString());
        Optional<User> user = userDomainService.findByKakaoId(info.getId());
        if (user.isEmpty()) {
            Optional.of(kakaoJoin(info));
        }
        return createToken(info.getEmail());
    }

    public TokenCheckResponse checkToken(String acsessToken, String email) {
        try {
            Claims claims = jwtProvider.parseJwtToken(acsessToken);
        } catch (ExpiredJwtException e) {
            UserToken userToken = userTokenDomainService.findByEmail(email).orElseThrow(() -> new RuntimeException());
            if (userToken.isExpire()) {
                return TokenCheckResponse.builder()
                        .code("401")
                        .msg("refresh token is expire")
                        .build();
            }

            return TokenCheckResponse.builder()
                    .code("200")
                    .msg("success")
                    .acsessToken(jwtProvider.createToken(email, accessTokenValidTime))
                    .build();
        }
        return TokenCheckResponse.builder()
                .code("200")
                .msg("success")
                .acsessToken(acsessToken)
                .build();
    }

    public TokenResponse createToken(String email) {
        String accessToken = jwtProvider.createToken(email, accessTokenValidTime);
        String refreshToken = jwtProvider.createToken(email, refreshTokenValidTime);

        saveRefreshToken(email, refreshToken);

        TokenDataResponse tokenDataResponse = TokenDataResponse.builder()
                .refreshToken(refreshToken)
                .acsessToken(accessToken)
                .key(email)
                .build();

        return TokenResponse.builder()
                .msg("ok")
                .code("200")
                .data(tokenDataResponse)
                .build();
    }

    public void saveRefreshToken(String userEmail, String refreshToken) {
        UserToken userToken = userTokenDomainService.findByEmail(userEmail).orElseThrow(() -> new RuntimeException());
        userToken.setRefreshToken(refreshToken);
        userTokenDomainService.save(userToken);
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
