package com.kakao.insurance.entity.collateral;

import com.kakao.insurance.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
}
