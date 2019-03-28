/**
 * Project Name:LoginServer-ShuBan
 * File Name:GameStateManager.java
 * Package Name:com.shuban.LoginServerShuBan.gameState.manager
 * Date:2019年3月18日下午3:25:10
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuban.LoginServerShuBan.work.gameState.model.primary.GameState;
import com.shuban.LoginServerShuBan.work.gameState.service.primary.IGameStateService;

/**
 * ClassName:GameStateManager 
 * Date:     2019年3月18日 下午3:25:10 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Service
public class GameStateManager {
	private final static Logger logger= LoggerFactory.getLogger(GameStateManager.class); 
	@Autowired
	private IGameStateService iGameStateService;
	
	
	public void saveOrUpdateGameState(GameState gameState){
		try {
			iGameStateService.save(gameState);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public Integer findState(String gameType){
		GameState gameState= findFirstByGameType(gameType);
		if(gameState== null)
			return 0;
		return gameState.getState();
	}
	
	
	public GameState findFirstByGameType(String gameType){
		GameState gameState= null;
		try{
			gameState= iGameStateService.findFirstByGameType(gameType);
			if(gameState== null){
				gameState= new GameState(gameType, 0);
			}
		}catch(Exception e){
			logger.error("", e);
		}
		return gameState;
	}
}

