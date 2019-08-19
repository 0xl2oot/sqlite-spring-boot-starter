package com.imwyh.sqlitestarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author yonghong
 * @date 2019-08-16
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(SQLiteProperties.class)
@ConditionalOnProperty(prefix = "imwyh.sqlite", name = "url")
public class SQLiteAutoConfiguration {

    private SQLiteProperties sqLiteProperties;

    @Autowired
    public SQLiteAutoConfiguration(SQLiteProperties sqLiteProperties) {
        this.sqLiteProperties = sqLiteProperties;
        log.info(sqLiteProperties.toString());
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(sqLiteProperties.getDriverClassName());
        dataSource.setUrl(sqLiteProperties.getUrl());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(sqLiteProperties.getPackageToScan());
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        if (sqLiteProperties.getDdlAuto() != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", sqLiteProperties.getDdlAuto());
        }
        if (sqLiteProperties.getDialect() != null) {
            hibernateProperties.setProperty("hibernate.dialect", sqLiteProperties.getDialect());
        }
        if (sqLiteProperties.getShowSql() != null) {
            hibernateProperties.setProperty("hibernate.show_sql", sqLiteProperties.getShowSql());
        }
        if (sqLiteProperties.getImplicitStrategy() != null) {
            hibernateProperties.setProperty("hibernate.implicit_naming_strategy", sqLiteProperties.getImplicitStrategy());
        }
        if (sqLiteProperties.getPhysicalStrategy() != null) {
            hibernateProperties.setProperty("hibernate.physical_naming_strategy", sqLiteProperties.getPhysicalStrategy());
        }
        return hibernateProperties;
    }
}
