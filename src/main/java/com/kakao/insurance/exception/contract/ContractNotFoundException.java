package com.kakao.insurance.exception.contract;

import com.kakao.insurance.common.exception.ExceptionCode;
import com.kakao.insurance.common.exception.UncheckedException;

/**
 * 계약이 존재하지 않습니다.
 */
public class ContractNotFoundException extends UncheckedException {

    public ContractNotFoundException() {
        super(ExceptionCode.CONTRACT_NOT_FOUND);
    }
}
