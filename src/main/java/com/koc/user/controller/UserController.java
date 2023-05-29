package com.koc.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/theme/health-check")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }

}
