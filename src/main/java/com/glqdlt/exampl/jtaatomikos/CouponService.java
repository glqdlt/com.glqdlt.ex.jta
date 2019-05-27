package com.glqdlt.exampl.jtaatomikos;

import com.glqdlt.exampl.jtaatomikos.db2.coupon.CouponUseLogRepository;
import com.glqdlt.exampl.jtaatomikos.db2.coupon.CouponUseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jhun
 * 2019-05-27
 */
@Slf4j
@Service
public class CouponService {

    @Autowired
    private CouponUseLogRepository couponUseLogRepository;

    @Transactional(transactionManager = "db2Txm")
    public CouponUseLog logging(CouponUseLog couponUseLog) {
       return  couponUseLogRepository.save(couponUseLog);
    }
}
