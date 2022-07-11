package com.kakao.insurance.dto.contract;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 상품 보험료 계산 결과
 */
@Getter
@Builder
public class ContractCalculateResult {

    /**
     * 상품아이디
     */
    private Long productId;

    /**
     * 상품명
     */
    private String productName;

    /**
     * 계약기간
     */
    private int contractMonths;

    /**
     * 담보목록
     */
    private List<ContractCollateralCalculateResult> collaterals;

    /**
     * 총 보험료를 계산합니다.
     * @return 총 보험료
     */
    public BigDecimal getTotalPremium() {
        if(collaterals == null || collaterals.isEmpty()) {
            return BigDecimal.valueOf(0.0);
        }

        return collaterals.stream()
                .map(ContractCollateralCalculateResult::getPremium)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
