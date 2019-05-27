package com.glqdlt.exampl.jtaatomikos;

import com.glqdlt.exampl.jtaatomikos.db1.member.MemberRepository;
import com.glqdlt.exampl.jtaatomikos.db1.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

/**
 * @author Jhun
 * 2019-05-27
 */
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(transactionManager = "db1Txm")
    public void execute(Member member, Long amount) {
        member.setAmount(amount);
        memberRepository.save(member);
    }

    @Transactional(transactionManager = "db1Txm", propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Member findMember(String userId) {
        return Optional.ofNullable(memberRepository.findByName(userId))
                .orElseThrow(() -> new RuntimeException(String.format("NotFounded user %s", userId)));
    }

    @Transactional(transactionManager = "db1Txm")
    public Member createMember(String name, Long amount){
        Member member = new Member();
        member.setAmount(amount);
        member.setName(name);
        return memberRepository.save(member);
    }
}
