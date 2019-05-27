package com.glqdlt.exampl.jtaatomikos;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Jhun
 * 2019-05-27
 */

@Configuration
public class DbConfig {

    @EnableJpaRepositories(
            basePackages = "com.glqdlt.exampl.jtaatomikos.db1",
            entityManagerFactoryRef = "db1Emf",
            transactionManagerRef = "db1Txm"
    )
    @Configuration
    public static class Db1 {

        @Autowired
        private Environment environment;


        @Bean("db1Ds")
        public DataSource dataSource() {
            MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
            mysqlXADataSource.setURL(environment.getProperty("d1.url"));
            mysqlXADataSource.setUser(environment.getProperty("d1.user"));
            mysqlXADataSource.setPassword(environment.getProperty("d1.password"));

            AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
            atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
            atomikosDataSourceBean.setUniqueResourceName("d1");
            return atomikosDataSourceBean;
        }

        @Bean("db1Emf")
        public LocalContainerEntityManagerFactoryBean emf(@Qualifier("db1Ds") DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            localContainerEntityManagerFactoryBean.setDataSource(dataSource);
            localContainerEntityManagerFactoryBean.setPackagesToScan("com.glqdlt.exampl.jtaatomikos.db1");
            localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            return localContainerEntityManagerFactoryBean;
        }

        @Bean(name = "db1Txm")
        public PlatformTransactionManager nmpTXM(@Qualifier("db1Emf") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

    }

    @EnableJpaRepositories(
            basePackages = "com.glqdlt.exampl.jtaatomikos.db2",
            entityManagerFactoryRef = "db2Emf",
            transactionManagerRef = "db2Txm"
    )
    @Configuration
    public static class Db2 {


        @Autowired
        private Environment environment;


        @Bean("db2Ds")
        public DataSource dataSource() {
            MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
            mysqlXADataSource.setURL(environment.getProperty("d2.url"));
            mysqlXADataSource.setUser(environment.getProperty("d2.user"));
            mysqlXADataSource.setPassword(environment.getProperty("d2.password"));

            AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
            atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
            atomikosDataSourceBean.setUniqueResourceName("d2");
            return atomikosDataSourceBean;
        }

        @Bean("db2Emf")
        public LocalContainerEntityManagerFactoryBean emf(@Qualifier("db2Ds") DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            localContainerEntityManagerFactoryBean.setDataSource(dataSource);
            localContainerEntityManagerFactoryBean.setPackagesToScan("com.glqdlt.exampl.jtaatomikos.db2");
            localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            return localContainerEntityManagerFactoryBean;
        }

        @Bean(name = "db2Txm")
        public PlatformTransactionManager nmpTXM(@Qualifier("db2Emf") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }


    }
}
