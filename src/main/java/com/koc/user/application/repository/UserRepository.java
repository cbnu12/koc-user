package com.koc.user.application.repository;

import com.koc.user.infrastructure.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByKakaoId(Long id);

    Optional<UserEntity> findByEmail(String email);
}
