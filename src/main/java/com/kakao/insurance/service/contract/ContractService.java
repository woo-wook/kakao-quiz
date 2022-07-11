package com.kakao.insurance.service.contract;

import com.kakao.insurance.dto.contract.ContractCollateralCalculateResult;
import com.kakao.insurance.dto.contract.ContractCalculateResult;
import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.exception.collateral.CollateralNotFoundException;
import com.kakao.insurance.exception.product.PossiblePeriodException;
import com.kakao.insurance.exception.product.ProductNotFoundException;
import com.kakao.insurance.repository.collateral.CollateralRepository;
import com.kakao.insurance.repository.contract.ContractRepository;
import com.kakao.insurance.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 계약 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final CollateralRepository collateralRepository;
    private final ProductRepository productRepository;

    /**
     * 계약의 예상 보험료를 조회합니다. (각 담보별 보험료를 같이 반환합니다.)
     *
     * @param productId 상품아이디
     * @param collateralIds 담보아이디목록
     * @param contractMonths 계약기간
     * @return 각 담보별 보험료와 총 보험료의 합
     * @see ContractCalculateResult
     */
    @Transactional(readOnly = true)
    public ContractCalculateResult calculate(Long productId, List<Long> collateralIds, int contractMonths) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        List<Collateral> collaterals = collateralRepository.findAllById(collateralIds);

        if(collaterals.isEmpty()) {
            throw new CollateralNotFoundException();
        }

        return calculate(product, collaterals, contractMonths);
    }

    /**
     * 계약의 예상 보험료를 조회합니다. (각 담보별 보험료를 같이 반환합니다.)
     *
     * @param product 상품
     * @param collaterals 담보목록
     * @param contractMonths 계약기간
     * @return 각 담보별 보험료와 총 보험료의 합
     * @see ContractCalculateResult
     */
    public ContractCalculateResult calculate(Product product, List<Collateral> collaterals, int contractMonths) {
        // 계약 기간 검증
        if(contractMonths == 0
                || product.getMinContractMonths() > contractMonths
                || product.getMaxContractMonths() < contractMonths) {
            throw new PossiblePeriodException();
        }

        return ContractCalculateResult.builder()
                .productId(product.getId())
                .productName(product.getName())
                .contractMonths(contractMonths)
                .collaterals(
                    collaterals.stream()
                            .map(
                                    collateral -> ContractCollateralCalculateResult.builder()
                                                    .collateralId(collateral.getId())
                                                    .collateralName(collateral.getName())
                                                    .premium(calculatePremium(collateral, contractMonths))
                                                    .build()
                            )
                            .collect(Collectors.toList())
                )
                .build();
    }

    /**
     * 담보의 예상 보험료를 조회합니다.
     * (납입기간 * 가입금액 / 기준금액) (소수점 3번째 자리 절사)
     * @param collateral 담보
     * @param contractMonths 계약기간
     * @return 담보의 예상 보험료
     */
    private BigDecimal calculatePremium(Collateral collateral, int contractMonths) {
        return BigDecimal.valueOf(contractMonths)
                .multiply(BigDecimal.valueOf(collateral.getInsureAmount()).divide(BigDecimal.valueOf(collateral.getBaseAmount()), 2, BigDecimal.ROUND_DOWN))
                .setScale(2, RoundingMode.DOWN);
    }
}
