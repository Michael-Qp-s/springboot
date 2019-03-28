/**
 * Project Name:LoginServer-ShuBan
 * File Name:GameState.java
 * Package Name:com.shuban.LoginServerShuBan.gameState.model
 * Date:2019年3月18日下午3:16:09
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.model.primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:GameState 
 * Date:     2019年3月18日 下午3:16:09 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "tb_game_state")
public class GameState {
	@Id
	@GenericGenerator(name= "systemUUID", strategy= "uuid")
	@GeneratedValue(generator= "systemUUID")
	private String id;
	
	@Column(name= "gameType")
	private String gameType;
	/**
	 * 状态值(0未开启 1开启)
	 */
	@Column(name= "state", columnDefinition= "int(2) default 0")
	private Integer state;
	/**
	 * 描述信息
	 */
	@Column(name= "state_desc")
	private String stateDesc;

	public GameState(String gameType, Integer state) {
		super();
		this.gameType = gameType;
		this.state = state;
	}
	
	
	
}

