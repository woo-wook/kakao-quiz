package com.kakao.insurance.exception.product;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 보험이 유효하지 않습니다.
 */
public class InvalidProductException  extends UncheckedException {

    public InvalidProductException() {
        super(ExceptionCode.INVALID_PRODUCT);
    }
}