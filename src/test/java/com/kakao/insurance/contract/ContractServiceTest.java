package com.kakao.insurance.contract;

import com.kakao.insurance.dto.contract.ContractCalculateResult;
import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.entity.contract.ContractStatus;
import com.kakao.insurance.service.contract.ContractService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private ContractService contractService;

    @Test
    public void 계약_목록_조회() throws Exception {
        // given
        Long travelProductId = 1L; // 여행자 보험
        List<Long> travelCollateralIds = Arrays.asList(1L); // 담보
        int travelContractMonths = 2; // 계약기간

        Contract contract = contractService.create(travelProductId, travelCollateralIds, travelContractMonths);
        
        // when
        List<Contract> contracts = contractService.findAll();

        // then
        contract = contracts.get(0);

        Assertions.assertNotNull(contract);
        Assertions.assertEquals(contract.getContractMonths(), travelContractMonths);
        Assertions.assertEquals(contract.getInsureStartDate(), LocalDate.now());
        Assertions.assertEquals(contract.getInsureEndDate(), LocalDate.now().plusMonths(travelContractMonths));
        Assertions.assertEquals(contract.getStatus(), ContractStatus.NORMAL);
    }

    @Test
    public void 계약_단건_조회() throws Exception {
        // given
        Long travelProductId = 1L; // 여행자 보험
        List<Long> travelCollateralIds = Arrays.asList(1L); // 담보
        int travelContractMonths = 2; // 계약기간

        Contract contract = contractService.create(travelProductId, travelCollateralIds, travelContractMonths);

        // when
        contract = contractService.findById(contract.getId());

        // then
        Assertions.assertNotNull(contract);
        Assertions.assertEquals(contract.getContractMonths(), travelContractMonths);
        Assertions.assertEquals(contract.getInsureStartDate(), LocalDate.now());
        Assertions.assertEquals(contract.getInsureEndDate(), LocalDate.now().plusMonths(travelContractMonths));
        Assertions.assertEquals(contract.getStatus(), ContractStatus.NORMAL);
    }

    @Test
    public void 계약_신규_생성() throws Exception {
        // given
        Long travelProductId = 1L; // 여행자 보험
        List<Long> travelCollateralIds = Arrays.asList(1L); // 담보
        int travelContractMonths = 2; // 계약기간

        // when
        Contract contract = contractService.create(travelProductId, travelCollateralIds, travelContractMonths);

        // then
        Assertions.assertNotNull(contract);
        Assertions.assertEquals(contract.getContractMonths(), travelContractMonths);
        Assertions.assertEquals(contract.getInsureStartDate(), LocalDate.now());
        Assertions.assertEquals(contract.getInsureEndDate(), LocalDate.now().plusMonths(travelContractMonths));
        Assertions.assertEquals(contract.getStatus(), ContractStatus.NORMAL);
    }
    
    @Test
    public void 예상_보험료_조회() throws Exception {
        // given
        Long travelProductId = 1L; // 여행자 보험
        List<Long> travelCollateralIds = Arrays.asList(1L); // 담보
        int travelContractMonths = 2; // 계약기간

        Long phoneProductId = 2L; // 여행자 보험
        List<Long> phoneCollateralIds = Arrays.asList(3L, 4L); // 담보
        int phoneContractMonths = 11; // 계약기간

        // when
        ContractCalculateResult travel = contractService.calculate(travelProductId, travelCollateralIds, travelContractMonths);
        ContractCalculateResult phone = contractService.calculate(phoneProductId, phoneCollateralIds, phoneContractMonths);

        // then
        Assertions.assertEquals(travel.getTotalPremium(), new BigDecimal("20000.00"));
        Assertions.assertEquals(phone.getTotalPremium(), new BigDecimal("648855.24"));
    }
}
