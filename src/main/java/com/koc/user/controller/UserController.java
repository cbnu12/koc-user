package com.koc.user.controller;

import com.koc.user.domain.User;
import com.koc.user.dto.UserDto;
import com.koc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

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
