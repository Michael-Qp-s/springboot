/**
 * Project Name:LoginServer-ShuBan
 * File Name:SecondConfig.java
 * Package Name:PrimaryConfig
 * Date:2019年3月27日下午3:06:40
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName:SecondConfig 
 * Date:     2019年3月27日 下午3:06:40 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecondary",
        transactionManagerRef = "transactionManagerSecondary",
        basePackages = {"com.shuban.LoginServerShuBan.work.*.service.second"}) //设置Repository所在位置
public class SecondConfig {
		@Resource
	    @Qualifier("secondaryDataSource")
	    private DataSource secondaryDataSource;
	 
	 	@Resource
	    private JpaProperties jpaProperties;
	 
	    @Autowired
	    private HibernateProperties hibernateProperties;
	 
	    @Bean(name = "entityManagerSecondary")
	    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
	        return entityManagerFactorySecondary(builder).getObject().createEntityManager();
	    }
	 
	    @Bean(name = "entityManagerFactorySecondary")
	    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder) {
	        LocalContainerEntityManagerFactoryBean entityManagerFactory
	                = builder
	                .dataSource(secondaryDataSource)
	                .packages("com.shuban.LoginServerShuBan.work.*.model.second")//设置实体类所在位置
	                .properties(getVendorProperties())
	                .persistenceUnit("secondaryPersistenceUnit")//持久化单元创建一个默认即可，多个便要分别命名
	                .build();
	        return entityManagerFactory;
	    }
	 
	    @Bean(name = "transactionManagerSecondary")
	    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
	        return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
	    }
	    
	    private Map<String, Object> getVendorProperties() {
	        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
	    }
}

