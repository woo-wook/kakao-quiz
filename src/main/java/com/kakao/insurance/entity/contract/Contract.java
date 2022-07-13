package com.kakao.insurance.entity.contract;

import com.kakao.insurance.entity.BaseEntity;
import com.kakao.insurance.entity.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
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

    /**
     * 생성자
     */
    @Builder
    public Contract(String contractNumber, Product product, int contractMonths,
                    LocalDate insureStartDate, LocalDate insureEndDate, ContractStatus status) {
        this.contractNumber = contractNumber;
        this.product = product;
        this.contractMonths = contractMonths;
        this.insureStartDate = insureStartDate;
        this.insureEndDate = insureEndDate;
        this.status = status;
    }

    /**
     * 계약을 생성합니다.
     * @param contractNumber 계약번호
     * @param product 계약상품
     * @param contractMonths 계약기간
     * @return 적용되는 계약정보
     */
    public static Contract create(String contractNumber, Product product, int contractMonths) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(contractMonths);

        return Contract.builder()
                .contractNumber(contractNumber)
                .product(product)
                .contractMonths(contractMonths)
                .insureStartDate(start)
                .insureEndDate(end)
                .status(ContractStatus.NORMAL)
                .build();
    }
}
