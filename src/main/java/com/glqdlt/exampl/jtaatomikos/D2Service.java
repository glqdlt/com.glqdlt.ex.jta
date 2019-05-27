package com.glqdlt.exampl.jtaatomikos;

import com.glqdlt.exampl.jtaatomikos.db2.Db2Repository;
import com.glqdlt.exampl.jtaatomikos.db2.Db2TxTester;
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
public class D2Service {

    @Autowired
    private Db2Repository db2Repository;

    @Transactional(transactionManager = "db2Txm")
    public void save(String value) {
        Db2TxTester txTester = new Db2TxTester();
        txTester.setValue(value);
        db2Repository.save(txTester);
    }
}
