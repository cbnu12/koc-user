package com.koc.user.controller;

import com.koc.user.service.authService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthConroller {

    private final authService authService;

    @GetMapping()
    public String login(@RequestParam String code) {
        return authService.login(code);
    }

    @GetMapping("/kakao-login-url")
    public String kakaoLogin() {
        return authService.getKakaoLoginUrl();
    }

}
