package com.kakao.insurance.exception.product;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 이미 해당 상품에 등록된 담보입니다.
 */
public class ProductCollateralDuplicateException extends UncheckedException {

    public ProductCollateralDuplicateException() {
        super(ExceptionCode.PRODUCT_COLLATERAL_DUPLICATE);
    }
}

