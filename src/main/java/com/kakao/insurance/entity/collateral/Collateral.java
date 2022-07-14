package com.kakao.insurance.entity.collateral;

import com.kakao.insurance.entity.BaseEntity;
import com.kakao.insurance.entity.product.ProductCollateral;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 담보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Collateral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "collateral_id")
    private Long id;

    /**
     * 담보명
     */
    @Column(length = 200)
    private String name;

    /**
     * 가입금액
     */
    private int insureAmount;

    /**
     * 기준금액
     */
    private int baseAmount;

    /**
     * 상품 매핑 정보
     */
    @OneToMany(mappedBy = "collateral", cascade = ALL, orphanRemoval = true)
    private List<ProductCollateral> products = new ArrayList<>();

    /**
     * 1달 보험료
     * @return (가입금액 / 기준금액)
     */
    private BigDecimal getMonthPremium() {
        return BigDecimal.valueOf(this.insureAmount)
                .divide(BigDecimal.valueOf(this.baseAmount), 2, RoundingMode.DOWN);
    }

    /**
     * 보험료 조회
     * @param contractMonths 계약기간
     * @return 해당 계약기간에 해당하는 보험료
     */
    public BigDecimal getPremium(int contractMonths) {
        return BigDecimal.valueOf(contractMonths)
                .multiply(getMonthPremium())
                .setScale(2, RoundingMode.DOWN);
    }

    /**
     * 생성자
     */
    @Builder
    public Collateral(String name, int insureAmount, int baseAmount) {
        this.name = name;
        this.insureAmount = insureAmount;
        this.baseAmount = baseAmount;
    }

    /**
     * 수정
     * @param name 담보명
     * @param insureAmount 가입금액 ( > 0)
     * @param baseAmount 기준금액 ( > 0)
     */
    public void update(String name, int insureAmount, int baseAmount) {
        this.name = name;
        this.insureAmount = insureAmount;
        this.baseAmount = baseAmount;
    }
}
