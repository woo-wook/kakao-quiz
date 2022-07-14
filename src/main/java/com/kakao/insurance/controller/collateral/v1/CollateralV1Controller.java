package com.kakao.insurance.controller.collateral.v1;

import com.kakao.insurance.dto.collateral.CollateralParam;
import com.kakao.insurance.dto.collateral.CollateralResult;
import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.service.collateral.CollateralService;
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

@Api(tags = "담보 관리")
@Slf4j
@RestController
@RequestMapping("/api/v1/collaterals")
@RequiredArgsConstructor
public class CollateralV1Controller {

    private final CollateralService collateralService;

    /**
     * 담보 > 담보 정보 목록 조회
     * @return 등록되어 있는 모든 담보 목록
     */
    @ApiOperation(value = "담보 > 담보 정보 목록 조회", notes = "현재 등록되어 있는 모든 담보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CollateralResult>> findAll() {
        log.debug("CollateralV1Controller :: findAll");

        List<Collateral> result = collateralService.findAll();

        return ResponseEntity.ok(
                result.stream()
                        .map(CollateralResult::toResult)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 담보 > 담보 정보 등록
     * @param param {@link CollateralParam}
     * @return 등록된 담보의 정보
     */
    @ApiOperation(value = "담보 > 담보 정보 등록", notes = "담보 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody final CollateralParam param) {
        log.debug("CollateralV1Controller :: create");

        Collateral collateral = collateralService.create(param.getName(), param.getInsureAmount(), param.getBaseAmount());

        return ResponseEntity
                .created(URI.create("/api/v1/collaterals/" + collateral.getId()))
                .build();
    }

    /**
     * 담보 > 담보 정보 조회
     * @param collateralId 담보의 아이디
     * @return 아이디에 해당하는 담보의 정보
     */
    @ApiOperation(value = "담보 > 담보 정보 조회", notes = "담보 정보를 조회합니다.")
    @GetMapping("/{collateralId}")
    public ResponseEntity<CollateralResult> findById(@PathVariable("collateralId") Long collateralId) {
        log.debug("CollateralV1Controller :: findById");

        Collateral collateral = collateralService.findById(collateralId);

        return ResponseEntity.ok(CollateralResult.toResult(collateral));
    }

    /**
     * 담보 > 담보 정보 수정
     * @param collateralId
     * @param param
     * @return
     */
    @ApiOperation(value = "담보 > 담보 수정", notes = "담보 정보를 수정합니다.")
    @PutMapping("/{collateralId}")
    public ResponseEntity<Void> update(@PathVariable("collateralId") Long collateralId, @Validated @RequestBody final CollateralParam param) {
        log.debug("CollateralV1Controller :: update");

        collateralService.update(collateralId, param.getName(), param.getInsureAmount(), param.getBaseAmount());

        return ResponseEntity
                .noContent().build();
    }
}
