package com.kakao.insurance.dto.contract;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 담보 보험료 계산 결과
 */
@Getter
@Builder
public class ContractCollateralCalculateResult {

    /**
     * 담보아이디
     */
    private Long collateralId;

    /**
     * 담보명
     */
    private String collateralName;

    /**
     * 보험료
     */
    private BigDecimal premium;
}
