package com.cmcc.andedu.microservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by LY on 2015/11/18.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.cmcc.andedu.microservice.module",
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDatasourceConfiguration {

    @ConfigurationProperties(prefix = "spring.jpa.primary")
    @Bean(name = "primaryJpaProperties")
    @Primary
    public JpaProperties primaryJpaProperties(){
        JpaProperties jpaProperties= new JpaProperties();
        return jpaProperties;
    }

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "primaryJdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "primaryJpaVendorAdapter")
    @Primary
    public JpaVendorAdapter primaryJpaVendorAdapter() {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        JpaProperties primaryJpaProperties = primaryJpaProperties();
        adapter.setShowSql(primaryJpaProperties.isShowSql());
        adapter.setDatabase(primaryJpaProperties.getDatabase());
        adapter.setDatabasePlatform(primaryJpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(primaryJpaProperties.isGenerateDdl());
        return adapter;
    }

    @Bean(name = "primaryEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource());
        em.setPackagesToScan(new String[]{"com.cmcc.andedu.microservice.module"});
        em.setJpaVendorAdapter(primaryJpaVendorAdapter());
        //em.setJpaPropertyMap(primaryJpaProperties().getProperties());
        JpaProperties primaryJpaProperties = primaryJpaProperties();
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", primaryJpaProperties.getHibernate().getDdlAuto());
        //properties.put("hibernate.dialect", getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(primaryEntityManager().getObject());
        return transactionManager;
    }


}
