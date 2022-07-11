package com.kakao.insurance.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 테이블 공통 컬럼
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    /**
     * 생성일시
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    /**
     * 생성자
     */
    @CreatedBy
    @Column(length = 50, updatable = false)
    private String createdBy;

    /**
     * 수정일시
     */
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedDate;

    /**
     * 수정자
     */
    @LastModifiedBy
    @Column(length = 50)
    private String modifiedBy;
}
