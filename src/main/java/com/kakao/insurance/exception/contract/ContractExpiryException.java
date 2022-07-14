package com.kakao.insurance.exception.contract;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 계약기간이 만료되었습니다.
 */
public class ContractExpiryException extends UncheckedException {

    public ContractExpiryException() {
        super(ExceptionCode.CONTRACT_EXPIRY);
    }
}
