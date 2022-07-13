package com.kakao.insurance.controller.contract.v1;

import com.kakao.insurance.dto.contract.ContractCalculateParam;
import com.kakao.insurance.dto.contract.ContractCalculateResult;
import com.kakao.insurance.service.contract.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractV1Controller {

    private final ContractService contractService;

    /**
     * 계약 > 예상 총 보험료 계산
     *
     * @param param {@link ContractCalculateParam}
     * @return 계산한 상품 정보와 금액
     */
    @GetMapping("/calculate")
    public ResponseEntity<ContractCalculateResult> calculate(ContractCalculateParam param) {
        log.debug("ContractV1Controller :: calculate");

        ContractCalculateResult result = contractService.calculate(param.getProductId(), param.getCollateralIds(), param.getContractMonths());

        return ResponseEntity.ok(result);
    }
}