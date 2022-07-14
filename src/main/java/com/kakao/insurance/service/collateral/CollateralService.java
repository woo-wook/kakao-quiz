package com.kakao.insurance.service.collateral;

import com.kakao.insurance.entity.collateral.Collateral;
import com.kakao.insurance.exception.collateral.CollateralNotFoundException;
import com.kakao.insurance.repository.collateral.CollateralRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 담보 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CollateralService {

    private final CollateralRepository collateralRepository;

    /**
     * 담보 목록 조회
     * @return 담보 목록
     */
    @Transactional(readOnly = true)
    public List<Collateral> findAll() {
        return collateralRepository.findAll();
    }

    /**
     * 담보 생성
     * @param name 담보명
     * @param insureAmount 가입금액 ( > 0)
     * @param baseAmount 기준금액 ( > 0)
     * @return 생성된 담보
     */
    @Transactional
    public Collateral create(String name, int insureAmount, int baseAmount) {
        Collateral collateral = Collateral.builder()
                .name(name)
                .insureAmount(insureAmount)
                .baseAmount(baseAmount)
                .build();

        return collateralRepository.save(collateral);
    }

    /**
     * 담보 조회
     * @param id 담보 아이디
     * @return 해당 아이디 담보의 정보
     */
    @Transactional(readOnly = true)
    public Collateral findById(Long id) {
        return collateralRepository.findById(id)
                .orElseThrow(CollateralNotFoundException::new);
    }

    /**
     * 담보 수정
     * @param id 담보 아이디
     * @param name 담보명
     * @param insureAmount 가입금액 ( > 0)
     * @param baseAmount 기준금액 ( > 0)
     * @return 수정된 담보 정보
     */
    @Transactional
    public Collateral update(Long id, String name, int insureAmount, int baseAmount) {
        Collateral collateral = collateralRepository.findById(id)
                .orElseThrow(CollateralNotFoundException::new);

        collateral.update(name, insureAmount, baseAmount);

        return collateral;
    }
}
