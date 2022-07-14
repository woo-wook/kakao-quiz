package com.kakao.insurance.dto.product;

import com.kakao.insurance.dto.collateral.CollateralResult;
import com.kakao.insurance.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("상품 결과")
@Getter
@Builder
public class ProductResult {

    @ApiModelProperty("상품아이디")
    private Long productId;

    @ApiModelProperty("상품명")
    private String name;

    @ApiModelProperty("최소 계약 개월 수")
    private int minContractMonths;

    @ApiModelProperty("최대 계약 개월 수")
    private int maxContractMonths;

    @ApiModelProperty(value = "유효기간 시작일", notes = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validityStartDate;

    @ApiModelProperty(value = "유효기간 종료일", notes = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validityEndDate;

    @ApiModelProperty(value = "생성일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "담보 목록")
    private List<CollateralResult> collaterals;

    /**
     * 결과 반환
     */
    public static ProductResult toResult(Product product) {
        return ProductResult.builder()
                .productId(product.getId())
                .name(product.getName())
                .minContractMonths(product.getMinContractMonths())
                .maxContractMonths(product.getMaxContractMonths())
                .validityStartDate(product.getValidityStartDate())
                .validityEndDate(product.getValidityEndDate())
                .createdDate(product.getCreatedDate())
                .collaterals(
                        product.getCollaterals().stream()
                                .map(x -> CollateralResult.toResult(x.getCollateral()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
