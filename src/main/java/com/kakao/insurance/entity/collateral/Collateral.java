package com.kakao.insurance.entity.collateral;

import com.kakao.insurance.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 담보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Collateral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "collateral_id")
    private Long id;

    /**
     * 담보명
     */
    @Column(length = 200)
    private String name;

    /**
     * 가입금액
     */
    private int insureAmount;

    /**
     * 기준금액
     */
    private int baseAmount;
}
