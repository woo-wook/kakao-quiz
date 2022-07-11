package com.kakao.insurance.exception.collateral;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 담보가 존재하지 않습니다.
 */
public class CollateralNotFoundException extends UncheckedException {
    public CollateralNotFoundException() {
        super(ExceptionCode.COLLATERAL_NOT_FOUND);
    }
}
