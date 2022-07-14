package com.kakao.insurance.repository.product;

import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.entity.collateral.QCollateral;
import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.entity.product.ProductCollateral;
import com.kakao.insurance.entity.product.QProductCollateral;
import com.kakao.insurance.support.querydsl.Querydsl4RepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kakao.insurance.entity.collateral.QCollateral.collateral;
import static com.kakao.insurance.entity.product.QProductCollateral.productCollateral;

@Repository
public class ProductCollateralQuerydslRepository extends Querydsl4RepositorySupport {

    public ProductCollateralQuerydslRepository() {
        super(ProductCollateral.class);
    }

    /**
     * 해당 상품에 담보가 맞는지 확인하기 위한 쿼리
     * @param product 상품
     * @param collaterals 담보 목록
     * @return 해당 상품에 해당하는 담보의 수
     */
    public Long countByCollaterals(Product product, List<Collateral> collaterals) {
        return select(productCollateral.id.count())
                .from(productCollateral)
                .innerJoin(productCollateral.collateral, collateral)
                .where(
                        productCollateral.product.eq(product),
                        productCollateral.collateral.in(collaterals)
                ).fetchOne();
    }
}
