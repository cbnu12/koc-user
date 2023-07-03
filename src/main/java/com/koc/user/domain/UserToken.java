package com.koc.user.domain;

import com.koc.user.application.dto.UserTokenDto;
import com.koc.user.application.jwt.JwtProvider;
import com.koc.user.infrastructure.Entity.UserTokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserToken {
    private Long id;
    private String email;
    private String refreshToken;

    private final JwtProvider jwtProvider;

    public UserTokenDto toDto() {
        return UserTokenDto.builder()
                .id(id)
                .email(email)
                .refreshToken(refreshToken)
                .build();
    }

    public UserTokenEntity toEntity() {
        return UserTokenEntity.builder()
                .id(id)
                .email(email)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean isExpire() {
        try {
            Claims claims = jwtProvider.parseJwtToken(refreshToken);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
