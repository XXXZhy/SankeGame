package com.jt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jt.util.DBUtil;
import com.jt.vo.UserVo;

/*DAO(Data Access Object)是一个数据访问接口，
  数据访问：顾名思义就是与数据库打交道。夹在业务逻辑与数据库资源中间。*/
public class UserDao {
	/*
	 * 根据用户名及密码查询用户
	 * 参数：用户名/密码
	 * 返回值：id、用户名、密码
	 */
	public UserVo login(String username,String password) {
		//初始化
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		UserVo user=null;
		
		
		try {
			//1.获取链接
			connection=DBUtil.getConnection();			
			//2.sql
			String sql="select * from user where username = ? and password = ?";
			//3.PreparedStatement
			pstmt = connection.prepareStatement(sql);
			//4.占位符赋值
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			//5.执行查询，得到结果集
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//有数据
				int id =rs.getInt(1);
				String name=rs.getString(2);
				String pwd=rs.getString(3);
				//6.操作结果集
				user = new UserVo(id,name,pwd);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//7.释放资源
			DBUtil.releaseDB(rs, pstmt, connection);
		}	
		//8.返回	
		return user;
	}
	
	/*
	 * 添加用户
	 * 参数：用户名、密码
	 * 返回：String结果
	 */
	
	@SuppressWarnings("resource")
	public String register (String username,String password) {
		//初始化
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		UserVo user=null;
		Boolean flag=false;
		//1.获取链接
		connection=DBUtil.getConnection();
		//2.sql
		String sql1="select * from user where username = ?";
		String sql2="insert into user values(null,?,?)";
		try {
			//3.PreparedStatement
			pstmt = connection.prepareStatement(sql1);
			//4.占位符赋值
			pstmt.setString(1, username);
			//5.执行查询，得到结果集
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//用户名已存在
				flag=true;	
			}else {
				//用户名不存在，加入新用户信息
				pstmt = connection.prepareStatement(sql2);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.execute();
				flag=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//7.释放资源
			DBUtil.releaseDB(rs, pstmt, connection);
		}	
		if(flag) {
			return "用户已存在";
		}else {
			return "注册成功";
		}
	}
	
}
