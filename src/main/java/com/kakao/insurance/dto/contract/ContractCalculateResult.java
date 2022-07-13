package com.kakao.insurance.dto.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 상품 보험료 계산 결과
 */
@ApiModel("상품 보험료 계산 결과")
@Getter
@Builder
public class ContractCalculateResult {

    /**
     * 상품아이디
     */
    @ApiModelProperty("상품아이디")
    private Long productId;

    /**
     * 상품명
     */
    @ApiModelProperty("상품명")
    private String productName;

    /**
     * 계약기간
     */
    @ApiModelProperty("계약기간")
    private int contractMonths;

    /**
     * 담보목록
     */
    @ApiModelProperty("담보목록")
    private List<ContractCollateralResult> collaterals;

    /**
     * 총 보험료를 계산합니다.
     * @return 총 보험료
     */
    @ApiModelProperty("총 보험료")
    public BigDecimal getTotalPremium() {
        if(collaterals == null || collaterals.isEmpty()) {
            return BigDecimal.valueOf(0.0);
        }

        return collaterals.stream()
                .map(ContractCollateralResult::getPremium)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
