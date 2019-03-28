/**
 * Project Name:LoginServer-ShuBan
 * File Name:PrimaryConfig.java
 * Package Name:com.shuban.LoginServerShuBan.base.dataSource
 * Date:2019年3月27日下午2:57:34
 *
*/

package com.shuban.LoginServerShuBan.base.dataSource;



import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName:PrimaryConfig 
 * Date:     2019年3月27日 下午2:57:34 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= { "com.shuban.LoginServerShuBan.work.*.service.primary" }) //设置Repository所在位置
public class PrimaryConfig {
	@Resource
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;
    @Resource
    private JpaProperties jpaProperties;
 
    @Autowired
    private HibernateProperties hibernateProperties;
    
    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }
 
 
    /**
     * 设置实体类所在位置
     */
    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(primaryDataSource)
                .packages("com.shuban.LoginServerShuBan.work.*.model.primary")
                .properties(getVendorProperties())
                .persistenceUnit("primaryPersistenceUnit")
                .build();
        return entityManagerFactory;
    }
 
    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
    

}

