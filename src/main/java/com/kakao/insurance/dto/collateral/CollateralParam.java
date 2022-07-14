package com.kakao.insurance.dto.collateral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("담보 파라미터")
@Getter
@Builder
public class CollateralParam {

    @ApiModelProperty("담보명")
    @NotNull(message = "담보명은 필수 파라미터입니다.")
    @NotEmpty(message = "담보명은 필수 파라미터입니다.")
    private String name;

    @ApiModelProperty("가입금액")
    @Min(value = 1, message = "가입금액은 1원 이상 입력 가능합니다.")
    private int insureAmount;

    @ApiModelProperty("기준금액")
    @Min(value = 1, message = "기준금액은 1원 이상 입력 가능합니다.")
    private int baseAmount;
}
