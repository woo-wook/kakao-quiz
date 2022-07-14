package com.kakao.insurance.dto.collateral;

import com.kakao.insurance.entity.collateral.Collateral;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("담보 결과")
@Getter
@Builder
public class CollateralResult {

    @ApiModelProperty("담보아이디")
    private Long collateralId;

    @ApiModelProperty("담보명")
    private String name;

    @ApiModelProperty("가입금액")
    private int insureAmount;

    @ApiModelProperty("기준금액")
    private int baseAmount;

    public static CollateralResult toResult(Collateral collateral) {
        return CollateralResult.builder()
                .collateralId(collateral.getId())
                .name(collateral.getName())
                .insureAmount(collateral.getInsureAmount())
                .baseAmount(collateral.getBaseAmount())
                .build();
    }
}
