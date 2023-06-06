package com.koc.user.service;

import com.koc.user.domain.User;
import com.koc.user.dto.UserDto;
import com.koc.user.service.domainService.UserDomainService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class UserService {
    private UserDomainService userDomainService;

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
