package com.kakao.insurance.repository.product;

import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.support.querydsl.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.kakao.insurance.entity.collateral.QCollateral.collateral;
import static com.kakao.insurance.entity.product.QProduct.product;
import static com.kakao.insurance.entity.product.QProductCollateral.productCollateral;

@Repository
public class ProductQuerydslRepository extends Querydsl4RepositorySupport {

    public ProductQuerydslRepository() {
        super(Product.class);
    }

    /**
     * 아이디로 상품의 정보를 조회합니다.
     * @param id 상품아이디
     * @return 상품 정보
     */
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(
                selectFrom(product)
                        .leftJoin(product.collaterals, productCollateral).fetchJoin()
                        .leftJoin(productCollateral.collateral, collateral).fetchJoin()
                        .where(
                                product.id.eq(id)
                        )
                        .fetchOne()
        );
    }
}
