package com.koc.user.service.domainService;

import com.koc.user.Entity.UserEntity;
import com.koc.user.domain.User;
import com.koc.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    public Optional<User> findByKakaoId(Long Id) {
        Optional<UserEntity> entity = userRepository.findByKakaoId(Id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(entity.get().toUser());
    }

    public Optional<User> findById(Long Id) {
        Optional<UserEntity> entity = userRepository.findById(Id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(entity.get().toUser());
    }

    public User save(User user) {
        return userRepository.save(user.toEntity()).toUser();
    }


}
