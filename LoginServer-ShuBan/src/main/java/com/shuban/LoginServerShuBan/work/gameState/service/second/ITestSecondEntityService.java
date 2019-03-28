/**
 * Project Name:LoginServer-ShuBan
 * File Name:ITestSecondEntityService.java
 * Package Name:com.shuban.LoginServerShuBan.work.gameState.service.second
 * Date:2019年3月27日下午3:48:53
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.service.second;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shuban.LoginServerShuBan.work.gameState.model.second.TestSecondEntity;

/**
 * ClassName:ITestSecondEntityService 
 * Date:     2019年3月27日 下午3:48:53 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
public interface ITestSecondEntityService extends JpaRepository<TestSecondEntity, String>{
	
}

