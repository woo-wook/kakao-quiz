package com.kakao.insurance.repository.contract;

import com.kakao.insurance.entity.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
