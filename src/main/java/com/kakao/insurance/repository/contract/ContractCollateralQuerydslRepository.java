package com.kakao.insurance.repository.contract;

import com.kakao.insurance.entity.contract.ContractCollateral;
import com.kakao.insurance.support.querydsl.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import static com.kakao.insurance.entity.contract.QContractCollateral.contractCollateral;

/**
 * 계약 담보 QueryDSL 레파지토리
 */
@Repository
public class ContractCollateralQuerydslRepository extends Querydsl4RepositorySupport {

    public ContractCollateralQuerydslRepository() {
        super(ContractCollateral.class);
    }

    /**
     * 계약 PK로 계약의 담보를 전부 제거합니다.
     * @param contractId 계약 PK
     * @return 삭제 수
     */
    public long deleteByContractId(Long contractId) {
        return getQueryFactory()
                .delete(contractCollateral)
                .where(
                        contractCollateral.contract.id.eq(contractId)
                ).execute();
    }
}
