package com.kakao.insurance.controller.contract.v1;

import com.kakao.insurance.dto.contract.*;
import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.service.contract.ContractService;
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

@Api(tags = "계약 관리")
@Slf4j
@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractV1Controller {

    private final ContractService contractService;

    /**
     * 계약 > 계약 정보 목록 조회
     * @return 등록되어있는 모든 계약
     */
    @ApiOperation(value = "계약 > 계약 정보 목록 조회", notes = "현재 등록되어 있는 모든 계약을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ContractListResult>> findAll() {
        log.debug("ContractV1Controller :: findAll");

        List<Contract> result = contractService.findAll();

        return ResponseEntity.ok(
                result.stream()
                        .map(ContractListResult::toResult)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 계약 > 계약 생성
     * @param param {@link ContractCreateParam}
     */
    @ApiOperation(value = "계약 > 계약 생성", notes = "신규 계약을 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody final ContractCreateParam param) {
        log.debug("ContractV1Controller :: create");

        Contract contract = contractService.create(param.getProductId(), param.getCollateralIds(), param.getContractMonths());

        return ResponseEntity
                .created(URI.create("/api/v1/contracts/" + contract.getId()))
                .build();
    }

    /**
     * 계약 > 계약 정보 조회
     * @param contractId 계약 PK
     * @return 해당 계약 정보
     */
    @ApiOperation(value = "계약 > 계약 정보 조회", notes = "계약 아이디로 계약을 조회합니다.")
    @GetMapping("/{contractId}")
    public ResponseEntity<ContractResult> findById(@PathVariable("contractId") Long contractId) {
        log.debug("ContractV1Controller :: findById");

        Contract result = contractService.findById(contractId);

        return ResponseEntity.ok(ContractResult.toResult(result));
    }

    /**
     * 계약 > 계약 정보 수정
     * @param contractId 계약 PK
     * @param param {@link ContractUpdateParam}
     */
    @ApiOperation(value = "계약 > 계약 정보 수정", notes = "계약을 수정합니다.\n계약 수정 시 보험료는 현재 시점의 담보 금액 기준으로 다시 계산하여 반영합니다.")
    @PutMapping("/{contractId}")
    public ResponseEntity<Void> update(@PathVariable("contractId") Long contractId, @Validated @RequestBody final ContractUpdateParam param) {
        log.debug("ContractV1Controller :: update");

        contractService.update(contractId, param.getCollateralIds(), param.getContractMonths(), param.getStatus());

        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * 계약 > 예상 총 보험료 계산
     *
     * @param param {@link ContractCalculateParam}
     * @return 계산한 상품 정보와 금액
     */
    @ApiOperation(value = "계약 > 예상 총 보험료 계산", notes = "해당 계약 진행 시 예상 총 보험료를 계산합니다.")
    @GetMapping("/calculate")
    public ResponseEntity<ContractCalculateResult> calculate(@Validated final ContractCalculateParam param) {
        log.debug("ContractV1Controller :: calculate");

        ContractCalculateResult result = contractService.calculate(param.getProductId(), param.getCollateralIds(), param.getContractMonths());

        return ResponseEntity.ok(result);
    }
}
