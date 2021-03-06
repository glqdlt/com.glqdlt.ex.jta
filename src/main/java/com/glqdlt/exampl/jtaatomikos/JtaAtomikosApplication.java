package com.glqdlt.exampl.jtaatomikos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JtaAtomikosApplication implements CommandLineRunner {

    @Autowired
    private CouponExecute couponExecute;

    @Autowired
    private MemberService memberService;

    public static void main(String[] args) {
        SpringApplication.run(JtaAtomikosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        memberService.createMember("tester", 0L);
        couponExecute.execute("tester", 999L);
    }
}
