/**
 * Project Name:LoginServer-ShuBan
 * File Name:TestSecondEntity.java
 * Package Name:com.shuban.LoginServerShuBan.work.gameState.model.secord
 * Date:2019年3月27日下午3:46:36
 *
*/

package com.shuban.LoginServerShuBan.work.gameState.model.second;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:TestSecondEntity 
 * Date:     2019年3月27日 下午3:46:36 
 * @author   QP
 * @version  
 * @since    JDK 1.7
 * @description    
 */
@Data
@Entity
@Table(name= "test_entity")
@NoArgsConstructor
public class TestSecondEntity {
	@Id
	@GenericGenerator(name= "systemUUID", strategy= "uuid")
	@GeneratedValue(generator= "systemUUID")
	private String id;
	
	private String column1;
	private String column2;
	public TestSecondEntity(String column1, String column2) {
		super();
		this.column1 = column1;
		this.column2 = column2;
	}
	
	
}

