package com.koc.user.controller;

import com.koc.user.dto.UserDto;
import com.koc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/health-check")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }

    @DeleteMapping("/{id}")
    public void withdraw(@PathVariable Long id) {
        userService.withdraw(id);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable long id) {
        return userService.findById(id);
    }


}
