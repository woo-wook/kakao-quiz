package com.kakao.insurance.product;

import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.exception.product.ProductCollateralDuplicateException;
import com.kakao.insurance.service.product.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void 상품_목록_조회() throws Exception {
        // given

        // when
        List<Product> result = productService.findAll();

        // then
        Assertions.assertEquals(result.size(), 2);

        Product product = result.get(0); // 대표 케이스 추출 (여행자 보험)

        Assertions.assertEquals(product.getName(), "여행자 보험");
        Assertions.assertEquals(product.getMinContractMonths(), 1);
        Assertions.assertEquals(product.getMaxContractMonths(), 3);
    }
    
    @Test
    public void 상품_생성() throws Exception {
        // given
        String name = "테스트 보험";
        int minContractMonths = 1;
        int maxContractMonths = 12;
        LocalDateTime validityStartDate = LocalDateTime.of(2022, 7, 14, 0, 0);
        LocalDateTime validityEndDate = LocalDateTime.of(2023, 7, 13, 23, 59);
        List<Long> collateralIds = Arrays.asList(1L, 3L, 4L);

        // when
        Product product = productService.create(name, minContractMonths, maxContractMonths, validityStartDate, validityEndDate, collateralIds);
        product = productService.findById(product.getId());

        // then
        Assertions.assertEquals(product.getName(), name);
        Assertions.assertEquals(product.getMinContractMonths(), minContractMonths);
        Assertions.assertEquals(product.getMaxContractMonths(), maxContractMonths);
        Assertions.assertEquals(product.getValidityStartDate(), validityStartDate);
        Assertions.assertEquals(product.getValidityEndDate(), validityEndDate);
        Assertions.assertEquals(product.getCollaterals().size(), 3);
    }
    
    @Test
    public void 상품_단건_조회() throws Exception {
        // given
        Long productId = 1L;
        
        // when
        Product product = productService.findById(productId);

        // then
        Assertions.assertEquals(product.getName(), "여행자 보험");
        Assertions.assertEquals(product.getMinContractMonths(), 1);
        Assertions.assertEquals(product.getMaxContractMonths(), 3);
    }
    
    @Test
    public void 상품_수정() throws Exception {
        // given
        Long productId = 1L;
        String name = "여행자 보험";
        int minContractMonths = 1;
        int maxContractMonths = 12;
        LocalDateTime validityStartDate = LocalDateTime.of(2022, 7, 14, 0, 0);
        LocalDateTime validityEndDate = LocalDateTime.of(2023, 7, 13, 23, 59);
        List<Long> addCollateralIds = Arrays.asList(4L);
        
        // when
        Product product = productService.update(productId, name, minContractMonths, maxContractMonths, validityStartDate, validityEndDate, addCollateralIds);
        product = productService.findById(product.getId());

        // then
        Assertions.assertEquals(product.getName(), name);
        Assertions.assertEquals(product.getMinContractMonths(), minContractMonths);
        Assertions.assertEquals(product.getMaxContractMonths(), maxContractMonths);
        Assertions.assertEquals(product.getValidityStartDate(), validityStartDate);
        Assertions.assertEquals(product.getValidityEndDate(), validityEndDate);
        Assertions.assertEquals(product.getCollaterals().size(), 3);
    }

    @Test
    public void 상품_수정_이미_등록한_담보() throws Exception {
        // given
        Long productId = 1L;
        String name = "여행자 보험";
        int minContractMonths = 1;
        int maxContractMonths = 12;
        LocalDateTime validityStartDate = LocalDateTime.of(2022, 7, 14, 0, 0);
        LocalDateTime validityEndDate = LocalDateTime.of(2023, 7, 13, 23, 59);
        List<Long> addCollateralIds = Arrays.asList(1L, 2L);

        // when & then
        Assertions.assertThrows(ProductCollateralDuplicateException.class, () -> {
            productService.update(productId, name, minContractMonths, maxContractMonths, validityStartDate, validityEndDate, addCollateralIds);
        });
    }

}
