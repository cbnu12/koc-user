package com.koc.user.presentation.api.usermanagement;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserManagementController {
    private final UserManagementService userManagementService;


    @DeleteMapping("/{id}")
    public void withdraw(@PathVariable Long id) {
        userManagementService.withdraw(id);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable long id) {
        return UserResponse.fromDto(userManagementService.getById(id));
    }
}
