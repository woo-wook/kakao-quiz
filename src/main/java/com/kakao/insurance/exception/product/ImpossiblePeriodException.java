package com.kakao.insurance.exception.product;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 계약이 불가능한 기간입니다.
 */
public class ImpossiblePeriodException extends UncheckedException {

    public ImpossiblePeriodException() {
        super(ExceptionCode.POSSIBLE_PERIOD);
    }
}
