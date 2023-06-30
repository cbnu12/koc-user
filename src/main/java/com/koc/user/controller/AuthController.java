package com.koc.user.controller;

import com.koc.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping()
    public String getToken(@RequestParam String code) {
        return authService.getToken(code);
    }

    @GetMapping("/kakao-login-url")
    public String kakaoLogin() {
        return authService.getKakaoLoginUrl();
    }

}
