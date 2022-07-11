package com.kakao.insurance.entity.product;

import com.kakao.insurance.entity.BaseEntity;
import com.kakao.insurance.entity.collateral.Collateral;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 상품 담보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCollateral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_collateral_id")
    private Long id;

    /**
     * 상품
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_collateral_01"))
    private Product product;

    /**
     * 담보
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "collateral_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_collateral_02"))
    private Collateral collateral;

    /**
     * 정렬순서
     */
    private int sort;
}
