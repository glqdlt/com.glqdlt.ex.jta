package com.glqdlt.exampl.jtaatomikos.db1.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jhun
 * 2019-05-27
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByName(String name);
}
