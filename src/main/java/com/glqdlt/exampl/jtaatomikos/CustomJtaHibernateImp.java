package com.glqdlt.exampl.jtaatomikos;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @author Jhun
 * @see  <a href ='https://stackoverflow.com/questions/20681245/how-to-use-atomikos-transaction-essentials-with-hibernate-4-3'>https://stackoverflow.com/questions/20681245/how-to-use-atomikos-transaction-essentials-with-hibernate-4-3</a>
 * 2019-05-27
 */
@Component
public class CustomJtaHibernateImp extends AbstractJtaPlatform {

    @Autowired
    @Qualifier("jtaTxm")
    private JtaTransactionManager jtaTransactionManager;


    @Override
    protected TransactionManager locateTransactionManager() {
        return jtaTransactionManager.getTransactionManager();
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return jtaTransactionManager.getUserTransaction();
    }
}
