package com.kakao.insurance.dto.product;

import com.kakao.insurance.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("상품 목록 결과")
@Getter
@Builder
public class ProductListResult {

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

    public static ProductListResult toResult(Product product) {
        return ProductListResult.builder()
                .productId(product.getId())
                .name(product.getName())
                .minContractMonths(product.getMinContractMonths())
                .maxContractMonths(product.getMaxContractMonths())
                .validityStartDate(product.getValidityStartDate())
                .validityEndDate(product.getValidityEndDate())
                .createdDate(product.getCreatedDate())
                .build();
    }
}
