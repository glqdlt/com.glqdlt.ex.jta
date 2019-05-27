package com.glqdlt.exampl.jtaatomikos.db1.member;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jhun
 * 2019-05-27
 */
@Entity(name = "db1_tx")
@Table(name = "tb_members")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;
    private String name;
    private Long amount;
    @Column(name = "reg_date")
    private Date regDate = new Date();

}
