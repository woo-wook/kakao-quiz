package com.kakao.insurance.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("상품 등록 파라미터")
@Getter
@Setter
public class ProductCreateParam {

    @ApiModelProperty("상품명")
    @NotNull(message = "상품명은 필수 파라미터입니다.")
    @NotEmpty(message = "상품명은 필수 파라미터입니다.")
    private String name;

    @ApiModelProperty("최소 계약 개월 수")
    @Min(value = 1, message = "최소 계약 개월수는 1개월 이상입니다.")
    private int minContractMonths;

    @ApiModelProperty("최대 계약 개월 수")
    @Min(value = 1, message = "최대 계약 개월수는 1개월 이상입니다.")
    private int maxContractMonths;

    @ApiModelProperty(value = "유효기간 시작일", notes = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validityStartDate;

    @ApiModelProperty(value = "유효기간 종료일", notes = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validityEndDate;

    @ApiModelProperty("담보 아이디 목록")
    @NotNull(message = "담보는 필수 파라미터입니다.")
    @NotEmpty(message = "담보는 필수 파라미터입니다.")
    private List<Long> collateralIds;
}
