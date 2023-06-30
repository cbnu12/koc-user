package com.koc.user.infra.kakao;

import com.koc.user.config.KakaoFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "kakaoClient", configuration = KakaoFeignConfiguration.class)
public interface KakaoClient {

    @PostMapping(headers = {"Content-Type=application/x-www-form-urlencoded;charset=utf-8"})
    KakaoTokenResponse getToken(URI baseUrl, @RequestParam("client_id") String restApiKey,
                                @RequestParam("redirect_uri") String redirectUrl,
                                @RequestParam("code") String code,
                                @RequestParam("grant_type") String grantType);

    @GetMapping
    KakaoUserInfoResponse getKakaoUserInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

}