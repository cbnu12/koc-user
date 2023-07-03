package com.koc.user.application.controller;

import com.koc.user.application.jwt.TokenCheckResponse;
import com.koc.user.application.jwt.TokenResponse;
import com.koc.user.application.service.authService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthConroller {

    private final authService authService;

    @GetMapping("/login")
    public TokenResponse login(@RequestParam String code) {
        return authService.login(code);
    }

    @GetMapping("/checkAcessToken")
    public TokenCheckResponse checkToken(@RequestHeader(value = "Authorization") String token, @RequestParam String email) throws Exception {

        return authService.checkToken(token, email);
    }

    @GetMapping("/kakao-login-url")
    public String kakaoLogin() {
        return authService.getKakaoLoginUrl();
    }

}
