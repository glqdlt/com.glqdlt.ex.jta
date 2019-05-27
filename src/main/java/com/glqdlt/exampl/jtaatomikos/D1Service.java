package com.glqdlt.exampl.jtaatomikos;

import com.glqdlt.exampl.jtaatomikos.db1.Db1Repository;
import com.glqdlt.exampl.jtaatomikos.db1.Db1TxTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jhun
 * 2019-05-27
 */
@Service
public class D1Service {

    @Autowired
    private Db1Repository db1Repository;

    @Transactional(transactionManager = "db1Txm")
    public void save(String value) {
        Db1TxTester txTester = new Db1TxTester();
        txTester.setValue(value);
        db1Repository.save(txTester);
    }
}
