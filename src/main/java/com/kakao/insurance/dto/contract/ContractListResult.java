package com.kakao.insurance.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.entity.contract.ContractStatus;
import com.kakao.insurance.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@ApiModel("계약 목록 결과")
@Getter
@Builder
public class ContractListResult {

    /**
     * 계약아이디
     */
    @ApiModelProperty("상품아이디")
    private Long contractId;

    /**
     * 계약번호
     */
    @ApiModelProperty("계약번호")
    private String contractNumber;

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
     * 보험 시작 일자
     */
    @ApiModelProperty("보험 시작 일자")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate insureStartDate;

    /**
     * 보험 종료 일자
     */
    @ApiModelProperty("보험 종료 일자")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate insureEndDate;

    /**
     * 계약상태
     */
    @ApiModelProperty("계약 상태")
    private ContractStatus status;

    /**
     * 결과 객체 변환
     * @param contract 변환할 계약
     * @return 결과 객체로 변환하여 반환
     */
    public static ContractListResult toResult(Contract contract) {
        Product product = contract.getProduct();

        return ContractListResult.builder()
                .contractId(contract.getId())
                .contractNumber(contract.getContractNumber())
                .productId(product.getId())
                .productName(product.getName())
                .contractMonths(contract.getContractMonths())
                .insureStartDate(contract.getInsureStartDate())
                .insureEndDate(contract.getInsureEndDate())
                .status(contract.getStatus())
                .build();
    }
}
