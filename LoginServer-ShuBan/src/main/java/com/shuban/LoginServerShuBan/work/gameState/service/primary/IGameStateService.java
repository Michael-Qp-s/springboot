/**
 * Project Name:LoginServer-ShuBan
 * File Name:IGameStateService.java
 * Package Name:com.shuban.LoginServerShuBan.gameState.service
 * Date:2019年3月18日下午3:23:19
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.service.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shuban.LoginServerShuBan.work.gameState.model.primary.GameState;

/**
 * ClassName:IGameStateService 
 * Date:     2019年3月18日 下午3:23:19 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
public interface IGameStateService extends JpaRepository<GameState, String> {
	
	
	GameState findFirstByGameType(String gameType);
}

