package com.kakao.insurance.dto.contract;

import com.kakao.insurance.entity.contract.ContractCollateral;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 계약 담보 결과
 */
@ApiModel("계약 담보 결과")
@Getter
@Builder
public class ContractCollateralResult {

    /**
     * 담보아이디
     */
    @ApiModelProperty("담보아이디")
    private Long collateralId;

    /**
     * 담보명
     */
    @ApiModelProperty("담보명")
    private String collateralName;

    /**
     * 보험료
     */
    @ApiModelProperty("보험료")
    private BigDecimal premium;

    /**
     * 결과 객체 변환
     * @param collateral 변환할 담보
     * @return 결과 객체로 변환하여 반환
     */
    public static ContractCollateralResult toResult(ContractCollateral collateral) {
        return ContractCollateralResult.builder()
                .collateralId(collateral.getId())
                .collateralName(collateral.getName())
                .premium(collateral.getPremium())
                .build();
    }
}
