package com.kakao.insurance.exception.product;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 상품이 존재하지 않습니다.
 */
public class ProductNotFoundException extends UncheckedException {

    public ProductNotFoundException() {
        super(ExceptionCode.PRODUCT_NOT_FOUND);
    }
}
