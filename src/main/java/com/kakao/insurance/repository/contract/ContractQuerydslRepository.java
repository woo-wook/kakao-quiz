package com.kakao.insurance.repository.contract;

import com.kakao.insurance.entity.collateral.QCollateral;
import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.entity.contract.QContract;
import com.kakao.insurance.entity.contract.QContractCollateral;
import com.kakao.insurance.entity.product.QProduct;
import com.kakao.insurance.support.querydsl.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.kakao.insurance.entity.collateral.QCollateral.collateral;
import static com.kakao.insurance.entity.contract.QContract.contract;
import static com.kakao.insurance.entity.contract.QContractCollateral.contractCollateral;
import static com.kakao.insurance.entity.product.QProduct.product;

/**
 * 계약 QueryDSL 레파지토리
 */
@Repository
public class ContractQuerydslRepository extends Querydsl4RepositorySupport {

    public ContractQuerydslRepository() {
        super(Contract.class);
    }

    /**
     * 계약 전체 목록 조회
     * @return 전체 계약의 목록
     */
    public List<Contract> findAll() {
        return selectFrom(contract)
                .innerJoin(contract.product, product).fetchJoin()
                .fetch();
    }

    /**
     * 계약 단건 조회
     * @param id
     * @return
     */
    public Optional<Contract> findById(Long id) {
        return Optional.ofNullable(
                selectFrom(contract)
                        .innerJoin(contract.product, product).fetchJoin()
                        .innerJoin(contract.collaterals, contractCollateral).fetchJoin()
                        .innerJoin(contractCollateral.collateral, collateral).fetchJoin()
                        .where(
                                contract.id.eq(id)
                        )
                        .fetchOne()
        );
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
