package com.kakao.insurance.entity.contract;

import com.kakao.insurance.entity.collateral.Collateral;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 계약담보
 *
 * 해당 테이블은 담보의 변경에 대비해 계약 당시 담보의 상태를 저장해두기 위해 생성된 테이블입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractCollateral {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "contract_collateral_id")
    private Long id;

    /**
     * 계약
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contract_id", nullable = false, foreignKey = @ForeignKey(name = "fk_contract_collateral_01"))
    private Contract contract;

    /**
     * 담보
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "collateral_id", nullable = false, foreignKey = @ForeignKey(name = "fk_contract_collateral_02"))
    private Collateral collateral;

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
     * 보험료
     */
    private double premium;
}
