package com.glqdlt.exampl.jtaatomikos.db2.coupon;

import com.glqdlt.exampl.jtaatomikos.db1.member.Member;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jhun
 * 2019-05-27
 */
@Entity(name = "db2_tx")
@Table(name = "tb_coupon_log")
@Data
public class CouponUseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
//    TODO 다른 엔티티 매니저에서 관리되는 Member 엔티티를 객체설정하고 싶었지만, 해당 엔티티를 못찾길래.. 불가능할 것으로 생각 됨.
//    @OneToOne
//    @JoinColumn
    private String memberName;
    private Long amount;
    @Column(name = "reg_date")
    private Date regDate = new Date();

    public static CouponUseLog valueOf(Member member, Long amount) {
        CouponUseLog couponUseLog = new CouponUseLog();
        couponUseLog.setMemberName(member.getName());
        couponUseLog.setAmount(amount);
        return couponUseLog;
    }
}
