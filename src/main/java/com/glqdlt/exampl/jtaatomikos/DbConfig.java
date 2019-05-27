package com.glqdlt.exampl.jtaatomikos;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

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
        private CustomJtaHibernateImp customJtaHibernateImp;

        @Autowired
        private Environment environment;


        @Bean("db1Ds")
        public DataSource dataSource() {
            JdbcDataSource h2Source = new JdbcDataSource();
            h2Source.setURL(environment.getProperty("d1.url"));
            h2Source.setUser(environment.getProperty("d1.user"));
            h2Source.setPassword(environment.getProperty("d1.password"));


            AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
            atomikosDataSourceBean.setXaDataSource(h2Source);
            atomikosDataSourceBean.setUniqueResourceName("d1");
            atomikosDataSourceBean.setMaxPoolSize(4);
            atomikosDataSourceBean.setTestQuery("select 1");
            atomikosDataSourceBean.setConcurrentConnectionValidation(true);
            return atomikosDataSourceBean;
        }

        @DependsOn("jtaTxm")
        @Bean("db1Emf")
        public LocalContainerEntityManagerFactoryBean emf(@Qualifier("db1Ds") DataSource dataSource) {

            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            localContainerEntityManagerFactoryBean.setJtaDataSource(dataSource);
            localContainerEntityManagerFactoryBean.setJpaPropertyMap(Db2.generateHibernateProps(customJtaHibernateImp));
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
        private CustomJtaHibernateImp customJtaHibernateImp;

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
            atomikosDataSourceBean.setMaxPoolSize(4);
            atomikosDataSourceBean.setConcurrentConnectionValidation(true);
            atomikosDataSourceBean.setTestQuery("select 1");
            return atomikosDataSourceBean;
        }

        public static HashMap<String, Object> generateHibernateProps(AbstractJtaPlatform abstractJtaPlatform) {
            HashMap<String, Object> properties = new HashMap<>();
            properties.put("hibernate.transaction.jta.platform", abstractJtaPlatform);
            properties.put("javax.persistence.transactionType", "JTA");
            properties.put("spring.jpa.hibernate.naming.physical-strategy",
                    "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
            return properties;
        }

        @DependsOn("jtaTxm")
        @Bean("db2Emf")
        public LocalContainerEntityManagerFactoryBean emf(@Qualifier("db2Ds") DataSource dataSource
        ) {

            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            localContainerEntityManagerFactoryBean.setPackagesToScan("com.glqdlt.exampl.jtaatomikos.db2");
            localContainerEntityManagerFactoryBean.setJpaPropertyMap(generateHibernateProps(customJtaHibernateImp));
            localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            localContainerEntityManagerFactoryBean.setJtaDataSource(dataSource);
            return localContainerEntityManagerFactoryBean;
        }

        @Bean(name = "db2Txm")
        public PlatformTransactionManager nmpTXM(@Qualifier("db2Emf") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
    }
}
