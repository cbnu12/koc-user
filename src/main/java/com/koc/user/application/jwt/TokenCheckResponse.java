package com.koc.user.application.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenCheckResponse {
    private String code;
    private String msg;
    private String acsessToken;
}
