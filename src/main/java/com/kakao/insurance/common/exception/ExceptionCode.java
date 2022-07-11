package com.kakao.insurance.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "정보가 없습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_SERVER_ERROR", "알 수 없는 오류가 발생했습니다. 해당 오류가 지속 될 경우, 고객센터로 문의해주세요.")
    ;

    private HttpStatus status;
    private String code;
    private String message;

    ExceptionCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
