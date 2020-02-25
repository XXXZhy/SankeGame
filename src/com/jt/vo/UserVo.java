package com.jt.vo;
/**
 * user表所对应的实体类
 * @author Zhy
 *	实体类中的属性对应表中的字段——》表中的一条记录，就可以用实体类的一个对象表示
 */
public class UserVo {
	private int id;//对应user表中的Id字段
	private String username;
	private String password;
	

	//无参构造器一定要有
	public UserVo() {
		super();
	}
	
	//有参构造器
	public UserVo(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
