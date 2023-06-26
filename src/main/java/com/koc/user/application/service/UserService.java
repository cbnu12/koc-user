package com.koc.user.application.service;

import com.koc.user.application.dto.UserDto;
import com.koc.user.application.service.domainService.UserDomainService;
import com.koc.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDomainService userDomainService;

    public User withdraw(Long id) {
        User user = userDomainService
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재 하지 않습니다."));
        user.withdraw();
        return userDomainService.save(user);
    }

    public UserDto findById(long id) {
        return userDomainService.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 사용자")).toDto();
    }
}
