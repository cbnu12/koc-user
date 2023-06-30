package com.koc.user.infra.kakao;

public class KakaoApiException extends RuntimeException{

    public KakaoApiException(String message) {
        super(message);
    }
}
