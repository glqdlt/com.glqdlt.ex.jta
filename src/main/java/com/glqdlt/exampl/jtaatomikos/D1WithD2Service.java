package com.glqdlt.exampl.jtaatomikos;

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
public class D1WithD2Service {

    private D1Service d1Service;

    private D2Service d2Service;

    public D1WithD2Service(@Autowired D1Service d1Service,
                           @Autowired D2Service d2Service) {
        this.d1Service = d1Service;
        this.d2Service = d2Service;
    }

    @Transactional(transactionManager = "jtaTxm")
    public void save() {
        d1Service.save("d1");
        d2Service.save("d2");
    }
}
