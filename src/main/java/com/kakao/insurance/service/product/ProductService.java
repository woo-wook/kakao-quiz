package com.kakao.insurance.service.product;

import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.entity.product.ProductCollateral;
import com.kakao.insurance.exception.collateral.CollateralNotFoundException;
import com.kakao.insurance.exception.product.ProductCollateralDuplicateException;
import com.kakao.insurance.exception.product.ProductNotFoundException;
import com.kakao.insurance.repository.collateral.CollateralRepository;
import com.kakao.insurance.repository.product.ProductQuerydslRepository;
import com.kakao.insurance.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 상품 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQuerydslRepository productQuerydslRepository;
    private final CollateralRepository collateralRepository;

    /**
     * 상품 목록 조회
     * @return 모든 상품 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 상품 생성
     * @param name 상품명
     * @param minContractMonths 최소 계약 가능 개월 수
     * @param maxContractMonths 최대 계약 가능 개월 수
     * @param validityStartDate 상품유효기간 시작일
     * @param validityEndDate 상품유효기간 종료일
     * @param collateralIds 담보 아이디 목록
     * @return 생성된 상품의 정보
     */
    @Transactional
    public Product create(String name, int minContractMonths, int maxContractMonths,
                          LocalDateTime validityStartDate, LocalDateTime validityEndDate, List<Long> collateralIds) {
        // 담보 조회 & 검증
        List<Collateral> collaterals = collateralRepository.findAllById(collateralIds);

        if(collaterals.isEmpty()) {
            throw new CollateralNotFoundException();
        }

        // 상품 등록
        Product product = Product.builder()
                .name(name)
                .minContractMonths(minContractMonths)
                .maxContractMonths(maxContractMonths)
                .validityStartDate(validityStartDate)
                .validityEndDate(validityEndDate)
                .build();

        productRepository.save(product);

        // 담보 등록
        collaterals.forEach(collateral ->
                        ProductCollateral.builder()
                                        .product(product)
                                        .collateral(collateral)
                                        .build()
        );

        return product;
    }

    /**
     * 상품 조회
     * @param id 상품 아이디
     * @return 해당 아이디의 상품 정보
     */
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productQuerydslRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    /**
     * 상품을 수정합니다.
     * @param productId 상품 아이디
     * @param name 상품명
     * @param minContractMonths 최소 계약 가능 개월 수
     * @param maxContractMonths 최대 계약 가능 개월 수
     * @param validityStartDate 상품유효기간 시작일
     * @param validityEndDate 상품유효기간 종료일
     * @param addCollateralIds 추가 상품 담보 아이디 목록
     * @return 수정된 상품 정보
     */
    @Transactional
    public Product update(Long productId, String name, int minContractMonths, int maxContractMonths,
                          LocalDateTime validityStartDate, LocalDateTime validityEndDate, List<Long> addCollateralIds) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        List<Collateral> collaterals = collateralRepository.findAllById(addCollateralIds);

        if(collaterals.isEmpty()) {
            throw new CollateralNotFoundException();
        }

        return update(product, name, minContractMonths, maxContractMonths, validityStartDate, validityEndDate, collaterals);
    }

    /**
     * 상품을 수정합니다.
     * @param product 상품
     * @param name 상품명
     * @param minContractMonths 최소 계약 가능 개월 수
     * @param maxContractMonths 최대 계약 가능 개월 수
     * @param validityStartDate 상품유효기간 시작일
     * @param validityEndDate 상품유효기간 종료일
     * @param addCollaterals 추가 상품 담보
     * @return 수정된 상품 정보
     */
    @Transactional
    public Product update(Product product, String name, int minContractMonths, int maxContractMonths,
                          LocalDateTime validityStartDate, LocalDateTime validityEndDate, List<Collateral> addCollaterals) {
        // 상품 수정
        product.update(
                name,
                minContractMonths,
                maxContractMonths,
                validityStartDate,
                validityEndDate
        );

        // 중복 담보가 존재하는지 검증
        if(product.getCollaterals().stream()
                .anyMatch(x -> addCollaterals.stream().anyMatch(y -> x.getCollateral().getId().equals(y.getId())))) {
            throw new ProductCollateralDuplicateException();
        }

        // 추가 담보 등록
        addCollaterals.forEach(collateral ->
                ProductCollateral.builder()
                        .product(product)
                        .collateral(collateral)
                        .build()
        );

        return product;
    }
}
