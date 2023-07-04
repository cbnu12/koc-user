package com.koc.user.presentation.api.usermanagement;

import com.koc.user.domain.user.UserDto;
import com.koc.user.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserService userService;

    public void withdraw(Long id) {
        userService.withdraw(id);
    }

    public UserDto getById(long id) {
        return userService.getUserById(id);

    }
}
