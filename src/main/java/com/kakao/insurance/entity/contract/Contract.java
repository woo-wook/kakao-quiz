package com.kakao.insurance.entity.contract;

import com.kakao.insurance.entity.BaseEntity;
import com.kakao.insurance.entity.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    /**
     * 계약번호
     */
    @Column(length = 20, unique = true)
    private String contractNumber;

    /**
     * 상품
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_contract_01"))
    private Product product;

    /**
     * 계약기간
     */
    private int contractMonths;

    /**
     * 보험 시작 일자
     */
    private LocalDate insureStartDate;

    /**
     * 보험 종료 일자
     */
    private LocalDate insureEndDate;

    /**
     * 계약상태
     */
    @Column(length = 10)
    @Enumerated(STRING)
    private ContractStatus status;

    /**
     * 계약 담보
     */
    @OneToMany(mappedBy = "contract", cascade = ALL, orphanRemoval = true)
    private List<ContractCollateral> collaterals = new ArrayList<>();
}
