package com.kakao.insurance.entity.product;

import com.kakao.insurance.entity.BaseEntity;
import com.kakao.insurance.entity.collateral.Collateral;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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
     * 생성자
     */
    @Builder
    public ProductCollateral(Product product, Collateral collateral) {
        Assert.notNull(product, "product must not be null!");
        Assert.notNull(collateral, "collateral must not be null!");

        this.product = product;
        this.collateral = collateral;

        this.product.getCollaterals().add(this);
    }
}
