package com.koc.user.domain.user;

import com.koc.user.infra.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isNotExistUserByKakaoId(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId).isEmpty();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .map(User::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자"));
        return user.toDto();
    }

    public void joinAsKakaoUser(Long kakaoUserId, String email) {
        User user = User.createKakaUser(kakaoUserId, email);

        userRepository.save(user.toEntity());
    }

    public void withdraw(Long id) {
        User user = userRepository.findById(id).map(User::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자"));
        user.withdraw();
        userRepository.save(user.toEntity());
    }
}
