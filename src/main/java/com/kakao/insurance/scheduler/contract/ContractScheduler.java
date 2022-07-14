package com.kakao.insurance.scheduler.contract;

import com.kakao.insurance.entity.contract.Contract;
import com.kakao.insurance.service.contract.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractScheduler {

    private final ContractService contractService;

    /**
     * 계약 만료 7일전 알림
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void sevenDaysBeforeContractExpire() {
        List<Contract> contracts = contractService.findListByContractExpireDays(7);

        // 만료 계약 존재
        if(contracts  != null && !contracts .isEmpty()) {
            for(Contract contract : contracts) {
                log.info("=========================");
                log.info("계약 만료 안내장 발송.");
                log.info("상품명 : {}", contract.getProduct().getName());
                log.info("만료일 : {}", contract.getInsureEndDate());
                log.info("=========================");
            }
        }
    }
}
