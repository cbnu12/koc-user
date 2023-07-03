package com.koc.user.application.repository;

import com.koc.user.infrastructure.Entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {
    Optional<UserTokenEntity> findByEmail(String email);

}
