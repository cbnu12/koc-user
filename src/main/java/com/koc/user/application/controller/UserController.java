package com.koc.user.application.controller;

import com.koc.user.application.dto.UserDto;
import com.koc.user.application.service.UserService;
import com.koc.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/health-check")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }

    @PutMapping("/withdraw")
    public User withdraw(@RequestBody UserDto userDto) {
        return userService.withdraw(userDto.getId());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable long id) {
        return userService.findById(id);
    }


}
