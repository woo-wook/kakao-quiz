package com.kakao.insurance.repository.collateral;

import com.kakao.insurance.entity.collateral.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollateralRepository extends JpaRepository<Collateral, Long> {
}
