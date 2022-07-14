package com.kakao.insurance.dto.contract;

import com.kakao.insurance.entity.contract.ContractStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 계약 수정 파라미터
 */
@ApiModel("계약 수정 파라미터")
@Getter
@Setter
public class ContractUpdateParam {

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

    /**
     * 계약상태
     */
    @ApiModelProperty(value = "계약상태")
    @NotNull(message = "계약 상태는 필수 파라미터입니다.")
    private ContractStatus status;
}
