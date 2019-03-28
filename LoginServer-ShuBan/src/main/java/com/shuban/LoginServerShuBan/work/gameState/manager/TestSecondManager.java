/**
 * Project Name:LoginServer-ShuBan
 * File Name:TestSecondManager.java
 * Package Name:com.shuban.LoginServerShuBan.work.gameState.manager
 * Date:2019年3月27日下午3:49:34
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuban.LoginServerShuBan.work.gameState.model.second.TestSecondEntity;
import com.shuban.LoginServerShuBan.work.gameState.service.second.ITestSecondEntityService;

import lombok.SneakyThrows;

/**
 * ClassName:TestSecondManager 
 * Date:     2019年3月27日 下午3:49:34 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Service
public class TestSecondManager {
	
	@Autowired
	private ITestSecondEntityService iTestSecondEntityService;
	
	
	@SneakyThrows
	public void save(){
		iTestSecondEntityService.save(new TestSecondEntity("column1", "column2"));
	}
}

