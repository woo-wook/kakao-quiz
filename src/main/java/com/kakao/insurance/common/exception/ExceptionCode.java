package com.kakao.insurance.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ExceptionCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND", "상품이 존재하지 않습니다."),
    POSSIBLE_PERIOD(HttpStatus.BAD_REQUEST, "POSSIBLE_PERIOD", "계약이 불가능한 기간입니다."),
    COLLATERAL_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLATERAL_NOT_FOUND", "담보가 존재하지 않습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_SERVER_ERROR", "알 수 없는 오류가 발생했습니다. 해당 오류가 지속 될 경우, 고객센터로 문의해주세요.")
    ;

    /**
     * 상태
     */
    private HttpStatus status;

    /**
     * 코드
     */
    private String code;

    /**
     * 내용
     */
    private String message;

    ExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
