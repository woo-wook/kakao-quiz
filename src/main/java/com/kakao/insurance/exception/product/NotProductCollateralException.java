package com.kakao.insurance.exception.product;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 해당 상품의 담보가 아닙니다.
 */
public class NotProductCollateralException extends UncheckedException {

    public NotProductCollateralException() {
        super(ExceptionCode.NOT_PRODUCT_COLLATERAL);
    }
}

