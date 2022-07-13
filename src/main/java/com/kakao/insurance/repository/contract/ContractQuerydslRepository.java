package com.kakao.insurance.repository.contract;

import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.entity.contract.QContract;
import com.kakao.insurance.support.querydsl.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static com.kakao.insurance.entity.contract.QContract.contract;

/**
 * 계약 QueryDSL 레파지토리
 */
@Repository
public class ContractQuerydslRepository extends Querydsl4RepositorySupport {

    public ContractQuerydslRepository() {
        super(Contract.class);
    }

    /**
     * 해당 일시의 마지막 계약번호를 조회합니다.
     * @param date 조회일시
     * @return 마지막 계약 번호
     */
    public Optional<String> getLastContractNumber(String date) {
        return Optional.ofNullable(
                select(contract.contractNumber)
                .from(contract)
                .where(
                        contract.contractNumber.startsWith(date)
                )
                .orderBy(
                        contract.contractNumber.desc()
                )
                .fetchFirst()
        );
    }
}
