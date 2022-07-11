package com.kakao.insurance.dto.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 계약 금액 계산 시 반드시 필요한 파라미터의 목록
 */
@Getter
@Setter
public class ContractCalculateParam {

    /**
     * 상품 아이디
     */
    private Long productId;

    /**
     * 담보 아이디 목록
     */
    private List<Long> collateralIds;

    /**
     * 계약기간
     */
    private int contractMonths;
}
