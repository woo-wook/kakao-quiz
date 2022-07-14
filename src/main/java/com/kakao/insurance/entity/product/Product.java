package com.kakao.insurance.entity.product;

import com.kakao.insurance.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 상품
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    /**
     * 아이디
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    /**
     * 상품명
     */
    @Column(length = 200)
    private String name;

    /**
     * 최소 계약 개월 수
     * (해당 데이터가 3일 경우, 해당 상품은 1개월 이상만 계약이 가능합니다.)
     */
    private int minContractMonths;

    /**
     * 최대 계약 개월 수
     * (해당 데이터가 7일 경우, 해당 상품은 7개월 이하만 계약이 가능합니다.)
     */
    private int maxContractMonths;

    /**
     * 유효기간(시작)
     */
    private LocalDateTime validityStartDate;

    /**
     * 유효기간(종료)
     */
    private LocalDateTime validityEndDate;

    /**
     * 담보 목록
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCollateral> collaterals = new ArrayList<>();

    /**
     * 생성자
     */
    @Builder
    public Product(String name, int minContractMonths, int maxContractMonths, LocalDateTime validityStartDate, LocalDateTime validityEndDate) {
        this.name = name;
        this.minContractMonths = minContractMonths;
        this.maxContractMonths = maxContractMonths;
        this.validityStartDate = validityStartDate;
        this.validityEndDate = validityEndDate;
    }

    /**
     * 수정
     */
    public void update(String name, int minContractMonths, int maxContractMonths, LocalDateTime validityStartDate, LocalDateTime validityEndDate) {
        this.name = name;
        this.minContractMonths = minContractMonths;
        this.maxContractMonths = maxContractMonths;
        this.validityStartDate = validityStartDate;
        this.validityEndDate = validityEndDate;
    }
}
