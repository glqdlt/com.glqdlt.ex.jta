package com.glqdlt.exampl.jtaatomikos.db2;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jhun
 * 2019-05-27
 */
@Entity(name = "db2_tx")
@Table(name = "mpoker_datadb.tb_cms_tx")
@Data
public class Db2TxTester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    private String value;

    private Date regDate = new Date();

}
