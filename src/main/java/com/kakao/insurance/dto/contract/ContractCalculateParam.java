package com.kakao.insurance.dto.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 계약 금액 계산 시 반드시 필요한 파라미터의 목록
 */
@ApiModel("계약 금액 계산 파라미터")
@Getter
@Setter
public class ContractCalculateParam {

    /**
     * 상품 아이디
     */
    @ApiModelProperty(value = "상품 아이디")
    @NotNull(message = "상품아이디는 필수 파라미터입니다.")
    private Long productId;

    /**
     * 담보 아이디 목록
     */
    @ApiModelProperty(value = "담보 아이디 목록")
    @NotNull(message = "담보 아이디 목록은 필수 파라미터입니다.")
    @NotEmpty(message = "담보 아이디 목록은 필수 파라미터입니다.")
    private List<Long> collateralIds;

    /**
     * 계약기간
     */
    @ApiModelProperty(value = "계약기간", required = true)
    @Min(value = 1, message = "최소 계약기간은 1달입니다.")
    private int contractMonths;
}
