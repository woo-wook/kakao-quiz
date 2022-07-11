package com.kakao.insurance.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResult {
    private String code;
    private String message;

    @Builder
    public ExceptionResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
