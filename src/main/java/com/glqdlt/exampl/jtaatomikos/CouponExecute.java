package com.glqdlt.exampl.jtaatomikos;

import com.glqdlt.exampl.jtaatomikos.db1.member.Member;
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
public class CouponExecute {

    private MemberService memberService;
    private CouponService couponService;

    public CouponExecute(@Autowired MemberService memberService,
                         @Autowired CouponService couponService) {
        this.memberService = memberService;
        this.couponService = couponService;
    }

    @Transactional(transactionManager = "jtaTxm")
    public void execute(String userId, Long amount) {
        Member target = memberService.findMember(userId);
        memberService.execute(target, amount);
        CouponUseLog couponUseLog = CouponUseLog.valueOf(target, amount);
        couponService.logging(couponUseLog);
    }

}
