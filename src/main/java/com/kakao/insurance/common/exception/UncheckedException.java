package com.kakao.insurance.common.exception;

import lombok.Getter;

@Getter
public class UncheckedException extends RuntimeException {
    private ExceptionCode error;

    public UncheckedException(ExceptionCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
