package com.kakao.insurance.service.contract;

import com.kakao.insurance.dto.contract.ContractCalculateResult;
import com.kakao.insurance.dto.contract.ContractCollateralResult;
import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.entity.contract.ContractCollateral;
import com.kakao.insurance.entity.contract.ContractStatus;
import com.kakao.insurance.entity.product.Product;
import com.kakao.insurance.exception.collateral.CollateralNotFoundException;
import com.kakao.insurance.exception.contract.ContractExpiryException;
import com.kakao.insurance.exception.contract.ContractNotFoundException;
import com.kakao.insurance.exception.product.ImpossiblePeriodException;
import com.kakao.insurance.exception.product.InvalidProductException;
import com.kakao.insurance.exception.product.NotProductCollateralException;
import com.kakao.insurance.exception.product.ProductNotFoundException;
import com.kakao.insurance.repository.collateral.CollateralRepository;
import com.kakao.insurance.repository.contract.ContractCollateralQuerydslRepository;
import com.kakao.insurance.repository.contract.ContractQuerydslRepository;
import com.kakao.insurance.repository.contract.ContractRepository;
import com.kakao.insurance.repository.product.ProductCollateralQuerydslRepository;
import com.kakao.insurance.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final ContractQuerydslRepository contractQuerydslRepository;
    private final ContractCollateralQuerydslRepository contractCollateralQuerydslRepository;
    private final CollateralRepository collateralRepository;
    private final ProductRepository productRepository;
    private final ProductCollateralQuerydslRepository productCollateralQuerydslRepository;

    /**
     * 계약 목록을 조회합니다.
     * @return 전체 계약 목록
     */
    @Transactional(readOnly = true)
    public List<Contract> findAll() {
        return contractQuerydslRepository.findAll();
    }

    /**
     * 계약을 조회합니다.
     * @param id 조회할 계약 PK
     * @return 계약정보
     */
    @Transactional(readOnly = true)
    public Contract findById(Long id) {
        return contractQuerydslRepository.findById(id)
                .orElseThrow(ContractNotFoundException::new);
    }

    /**
     * 계약을 생성합니다.
     * @param productId 계약 상품 아이디
     * @param collateralIds 계약 담보물 아이디 목록
     * @param contractMonths 계약기간
     * @return 생성된 계약 정보
     */
    @Transactional
    public Contract create(Long productId, List<Long> collateralIds, int contractMonths) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        List<Collateral> collaterals = collateralRepository.findAllById(collateralIds);

        if(collaterals.isEmpty()) {
            throw new CollateralNotFoundException();
        }

        return create(product, collaterals, contractMonths);
    }

    /**
     * 계약을 생성합니다.
     * @param product 계약 상품
     * @param collaterals 계약 담보물 목록
     * @param contractMonths 계약기간
     * @return 생성된 계약 정보
     */
    @Transactional
    public Contract create(Product product, List<Collateral> collaterals, int contractMonths) {
        // 상품 검증
        validProduct(product, collaterals, contractMonths);

        // 계약 생성
        Contract contract = Contract.create(generateContractNumber(), product, contractMonths);
        contractRepository.save(contract);

        // 담보 생성
        collaterals.forEach(collateral ->
                        ContractCollateral.builder()
                                .contract(contract)
                                .collateral(collateral)
                                .build()
                );

        return contract;
    }

    /**
     * 계약을 수정합니다.
     * @param contractId 계약 PK
     * @param collateralIds 담보 목록 ID
     * @param contractMonths 계약기간
     * @param status 계약상태
     * @return 수정된 계약 정보
     */
    @Transactional
    public Contract update(Long contractId, List<Long> collateralIds, int contractMonths, ContractStatus status) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(ContractNotFoundException::new);

        List<Collateral> collaterals = collateralRepository.findAllById(collateralIds);

        if(collaterals.isEmpty()) {
            throw new CollateralNotFoundException();
        }

        return update(contract, collaterals, contractMonths, status);
    }

    /**
     * 계약을 수정합니다.
     * @param contract 계약
     * @param collaterals 담보
     * @param contractMonths 계약기간
     * @param status 계약상태
     * @return 수정된 계약 정보
     */
    @Transactional
    public Contract update(Contract contract, List<Collateral> collaterals, int contractMonths, ContractStatus status) {
        if(contract.getStatus() == ContractStatus.EXPIRY) {
            throw new ContractExpiryException();
        }

        // 상품 검증
        validProduct(contract.getProduct(), collaterals, contractMonths);

        // 기간정보 수정
        contract.update(contractMonths, status);

        // 담보 정보 일괄 제거
        contractCollateralQuerydslRepository.deleteByContractId(contract.getId());

        // 담보 등록(수정 시 금액 재 계산)
        collaterals.forEach(collateral ->
                ContractCollateral.builder()
                        .contract(contract)
                        .collateral(collateral)
                        .build()
        );

        return contract;
    }

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
        return ContractCalculateResult.builder()
                .productId(product.getId())
                .productName(product.getName())
                .contractMonths(contractMonths)
                .collaterals(
                    collaterals.stream()
                            .map(
                                    collateral -> ContractCollateralResult.builder()
                                                    .collateralId(collateral.getId())
                                                    .collateralName(collateral.getName())
                                                    .premium(collateral.getPremium(contractMonths))
                                                    .build()
                            )
                            .collect(Collectors.toList())
                )
                .build();
    }

    /**
     * N일 후 만료되는 계약 목록을 조회합니다.
     * @param days N Days
     * @return 계약의 목록
     */
    @Transactional(readOnly = true)
    public List<Contract> findListByContractExpireDays(int days) {
        // 검색일자
        LocalDate searchDate = LocalDate.now().plusDays(days);
        return contractQuerydslRepository.findListByInsureEndDate(searchDate);
    }

    /**
     * 계약 번호를 생성합니다.
     * @return 생성된 계약 번호
     */
    private String generateContractNumber() {
        // 현재일자 조회
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 번호 추출
        String lastContractNumber = contractQuerydslRepository.getLastContractNumber(nowDate)
                .orElseGet(() -> nowDate.concat("0000"));
        int key = Integer.parseInt(lastContractNumber.substring(lastContractNumber.length() - 4));

        return String.format("%s%04d", nowDate, key + 1);
    }

    /**
     * 상품의 계약기간 및 가입 가능한 담보인지 검증합니다.
     * @param product 상품
     * @param collaterals 담보 목록
     */
    private void validProduct(Product product, List<Collateral> collaterals, int contractMonths) {
        if(contractMonths == 0
                || product.getMinContractMonths() > contractMonths
                || product.getMaxContractMonths() < contractMonths) {
            throw new ImpossiblePeriodException();
        }

        LocalDateTime now = LocalDateTime.now();

        if(now.isBefore(product.getValidityStartDate()) || now.isAfter(product.getValidityEndDate())) {
            throw new InvalidProductException();
        }

        Long productCollateralsCount = productCollateralQuerydslRepository.countByCollaterals(product, collaterals);

        if(productCollateralsCount == null || productCollateralsCount != collaterals.size()) {
            throw new NotProductCollateralException();
        }
    }
}
