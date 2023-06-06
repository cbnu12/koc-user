package com.koc.user.service;

import com.koc.user.domain.User;
import com.koc.user.service.domainService.UserDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
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
}
