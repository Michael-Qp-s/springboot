/**
 * Project Name:LoginServer-ShuBan
 * File Name:MainAction.java
 * Package Name:com.shuban.LoginServerShuBan.action
 * Date:2019年3月18日下午3:12:10
 *
*/
package com.shuban.LoginServerShuBan.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shuban.LoginServerShuBan.util.StringUtils;
import com.shuban.LoginServerShuBan.work.gameState.manager.GameStateManager;
import com.shuban.LoginServerShuBan.work.gameState.model.primary.GameState;

import net.sf.json.JSONObject;

/**
 * ClassName:MainAction Date: 2019年3月18日 下午3:12:10
 * @author QP
 * @version
 * @since JDK 1.7
 * @description
 */
@RestController
public class MainAction {
	private final static Logger logger = LoggerFactory.getLogger(MainAction.class);
	@Autowired
	private GameStateManager gameStateManager;

	@GetMapping("game_state_get")
	private String gameStateGet(String gameType) {
		logger.info("request game_state_get");
		Integer status = 0;
		String desc = "";
		try {
			if (gameType == null) {
				status = 0;
				desc = "";
			} else {
				GameState gameState = gameStateManager.findFirstByGameType(gameType);
				status = gameState.getState();
				desc = gameState.getStateDesc();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		JSONObject jsonObj= new JSONObject();
		jsonObj.put("status", status);
		jsonObj.put("desc", desc);
		logger.info(jsonObj.toString());
		return jsonObj.toString();
	}
	@GetMapping("game_state_update")
	private String gameStateUpdate(String gameType, Integer state, String desc) {
		logger.info("request game_state_update");
		JSONObject obj = new JSONObject();
		try {
			if (StringUtils.isEmpty(gameType)) {
				obj.put("status", -1);
				obj.put("msg", "参数有空");
				return obj.toString();
			}
			if (state != 1 && state != 0) {
				obj.put("status", -2);
				obj.put("msg", "参数state有错误");
				return obj.toString();
			}
			GameState gameState = gameStateManager.findFirstByGameType(gameType);
			if (gameState == null) {
				gameState = new GameState(gameType, state);
				gameState.setStateDesc(desc);
				gameStateManager.saveOrUpdateGameState(gameState);
			}
			gameState.setState(state);
			if (state == 0)
				gameState.setStateDesc(desc);
			else
				gameState.setStateDesc("开服中");
			gameStateManager.saveOrUpdateGameState(gameState);
			obj.put("status", 0);
			obj.put("msg", "修改成功");
		} catch (Exception e) {
			logger.error("", e);
			obj.put("status", -3);
			obj.put("msg", "系统错误");
			return obj.toString();
		}
		logger.info(obj.toString());
		return obj.toString();
	}
}
