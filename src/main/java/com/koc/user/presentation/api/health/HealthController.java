package com.koc.user.presentation.api.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health-check")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }
}
