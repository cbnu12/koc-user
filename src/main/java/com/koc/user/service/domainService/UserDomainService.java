package com.koc.user.service.domainService;

import com.koc.user.entity.UserEntity;
import com.koc.user.domain.User;
import com.koc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    public Optional<User> findByKakaoId(Long Id) {
        Optional<UserEntity> entity = userRepository.findByKakaoId(Id);

        return entity.map(UserEntity::toUser);
    }

    public Optional<User> findById(Long Id) {
        Optional<UserEntity> entity = userRepository.findById(Id);
        return entity.map(UserEntity::toUser);
    }

    public User save(User user) {
        return userRepository.save(user.toEntity()).toUser();
    }


}
