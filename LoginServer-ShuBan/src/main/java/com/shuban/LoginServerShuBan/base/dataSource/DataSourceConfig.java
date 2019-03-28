/**
 * Project Name:LoginServer-ShuBan
 * File Name:DataSourceConfig.java
 * Package Name:com.shuban.LoginServerShuBan.base.dataSource
 * Date:2019年3月27日下午3:44:37
 *
*/

package com.shuban.LoginServerShuBan.base.dataSource;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ClassName:DataSourceConfig 
 * Date:     2019年3月27日 下午3:44:37 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Configuration
public class DataSourceConfig {
		@Bean("primaryDataSource")
	    @Primary
	    @ConfigurationProperties(prefix = "spring.datasource.def")
	    public DataSource dataSource(){
	        return DataSourceBuilder.create().build();
	    }
	    @Bean("secondaryDataSource")
	    @ConfigurationProperties(prefix = "spring.datasource.second")
	    public DataSource dataSourceSlave(){
	        return DataSourceBuilder.create().build();
	    }
}

