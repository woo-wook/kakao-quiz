package com.kakao.insurance.controller.product.v1;

import com.kakao.insurance.dto.product.ProductCreateParam;
import com.kakao.insurance.dto.product.ProductListResult;
import com.kakao.insurance.dto.product.ProductResult;
import com.kakao.insurance.dto.product.ProductUpdateParam;
import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "상품 관리")
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductV1Controller {

    private final ProductService productService;

    /**
     * 상품 > 상품 목록 조회
     * @return 모든 상품의 목록을 조회합니다.
     */
    @ApiOperation(value = "상품 > 상품 목록 조회", notes = "상품의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductListResult>> findAll() {
        log.debug("ProductV1Controller :: findAll");

        List<Product> result = productService.findAll();

        return ResponseEntity.ok(
                result.stream()
                        .map(ProductListResult::toResult)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 상품 > 상품 생성
     * @param param {@link ProductCreateParam}
     * @return 생성된 상품의 정보
     */
    @ApiOperation(value = "상품 > 상품 생성", notes = "상품을 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody final ProductCreateParam param) {
        log.debug("ProductV1Controller :: create");

        Product product = productService.create(
                param.getName(),
                param.getMinContractMonths(),
                param.getMaxContractMonths(),
                param.getValidityStartDate(),
                param.getValidityEndDate(),
                param.getCollateralIds()
        );

        return ResponseEntity
                .created(URI.create("/api/v1/products/" + product.getId())).build();
    }

    /**
     * 상품 > 상품 정보 조회
     * @param productId 상품 아이디
     * @return 해당하는 아이디의 상품 정보
     */
    @ApiOperation(value = "상품 > 상품 정보 조회", notes = "상품의 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResult> findById(@PathVariable("productId") Long productId) {
        log.debug("ProductV1Controller :: findById");

        Product product = productService.findById(productId);

        return ResponseEntity.ok(ProductResult.toResult(product));
    }

    /**
     * 상품 > 상품 정보 수정
     * @param productId 상품 아이디
     * @param param {@link ProductUpdateParam}
     * @return 수정된 상품의 정보
     */
    @ApiOperation(value = "상품 > 상품 정보 수정", notes = "상품의 정보를 수정합니다.")
    @PutMapping("/{productId}")
    public ResponseEntity<Void> update(@PathVariable("productId") Long productId, @Validated @RequestBody final ProductUpdateParam param) {
        log.debug("ProductV1Controller :: update");

        productService.update(
                productId,
                param.getName(),
                param.getMinContractMonths(),
                param.getMaxContractMonths(),
                param.getValidityStartDate(),
                param.getValidityEndDate(),
                param.getAddCollateralIds()
        );

        return ResponseEntity.noContent().build();
    }
}
