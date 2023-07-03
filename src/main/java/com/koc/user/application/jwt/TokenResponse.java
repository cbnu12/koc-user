package com.koc.user.application.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String code;
    private String msg;
    private TokenDataResponse data;
}
