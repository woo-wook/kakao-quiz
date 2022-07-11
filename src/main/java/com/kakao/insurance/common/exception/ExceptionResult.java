package com.kakao.insurance.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResult {
    private String code;
    private String message;

    @Builder
    public ExceptionResult(HttpStatus status, String code, String message) {
        this.code = code;
        this.message = message;
    }
}
