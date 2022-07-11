package com.kakao.insurance.entity.contract;

import lombok.Getter;

/**
 * 계약 상태
 */
@Getter
public enum ContractStatus {
    NORMAL("정상계약"), WITHDRAW("청약철회"), EXPIRY("기간만료")
    ;

    private String name;

    ContractStatus(String name) {
        this.name = name;
    }
}
