package com.kakao.insurance.collateral;

import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.service.collateral.CollateralService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CollateralServiceTest {

    @Autowired
    private CollateralService collateralService;

    @Test
    public void 담보_목록_조회() throws Exception {
        // given

        // when
        List<Collateral> result = collateralService.findAll();

        // then
        Collateral collateral = result.get(0); // 대표 케이스 추출 (상해치료비)

        Assertions.assertEquals(collateral.getName(), "상해치료비");
        Assertions.assertEquals(collateral.getInsureAmount(), 1000000);
        Assertions.assertEquals(collateral.getBaseAmount(), 100);
    }
    
    @Test
    public void 담보_생성() throws Exception {
        // given
        String name = "암진단비";
        int insureAmount = 30000000;
        int baseAmount = 150;
        
        // when
        Collateral collateral = collateralService.create(name, insureAmount, baseAmount);

        // then
        Assertions.assertEquals(collateral.getName(), name);
        Assertions.assertEquals(collateral.getInsureAmount(), insureAmount);
        Assertions.assertEquals(collateral.getBaseAmount(), baseAmount);
    }
    
    @Test
    public void 담보_단건_조회() throws Exception {
        // given
        Long collateralId = 1L;
        
        // when
        Collateral collateral = collateralService.findById(collateralId);

        // then
        Assertions.assertEquals(collateral.getName(), "상해치료비");
        Assertions.assertEquals(collateral.getInsureAmount(), 1000000);
        Assertions.assertEquals(collateral.getBaseAmount(), 100);
    }

    @Test
    public void 담보_수정() throws Exception {
        // given
        Long collateralId = 5L;
        String name = "암치료비";
        int insureAmount = 20000000;
        int baseAmount = 50;

        // when
        Collateral collateral = collateralService.update(collateralId, name, insureAmount, baseAmount);

        // then
        Assertions.assertEquals(collateral.getName(), name);
        Assertions.assertEquals(collateral.getInsureAmount(), insureAmount);
        Assertions.assertEquals(collateral.getBaseAmount(), baseAmount);
    }
}
