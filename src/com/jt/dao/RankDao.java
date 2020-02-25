package com.jt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jt.util.Config;
import com.jt.util.DBUtil;
import com.jt.vo.RankVo;

/**
 * 对rank表操作的类
 * @author Zhy
 *
 */
public class RankDao {
	
	
	
	/**
	 * 向rank表中添加成绩,并删除以前的成绩
	 * @param id 登录用户ID=Config.userID
	 * @param score 分数=Score.getscore()
	 */
	public void insertScore(int id,int score) {
		Connection connection =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			connection = DBUtil.getConnection();
			String sql1="delete from rank"+Config.mapID+" where userID=?";
			String sql2="insert into rank"+Config.mapID+" values(null,?,?,?);";
			
			//1.执行删除语句sql1，删除历史最高级记录
			pstmt = connection.prepareStatement(sql1);
			pstmt.setInt(1, id);
			pstmt.execute();
			
			//2.增加新的最高分记录
			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, score);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String currentDate =   dateFormat.format( new Date() );//当前时间字符串currentDate
			pstmt.setString(2, currentDate);
			pstmt.setInt(3, id);
			pstmt.execute();
			
			//释放资源
			DBUtil.releaseDB(rs, pstmt, connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询用户分数
	 * @param id=User.getID()  
	 * @return 分数score
	 */
	public int selectScore(int id) {
		Connection connection =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int score=0;//新用户分数默认0
		
		try {
			//1.获取连接Connection
			connection = DBUtil.getConnection();
			//2.写sql
			String sql="SELECT score FROM rank"+Config.mapID+" where userID = ? ";
			//3.获取PreparedStatement
			pstmt = connection.prepareStatement(sql);
			//4.如果sql中有占位符，需要占位符赋值
			pstmt.setInt(1, id);
			//5.执行查询得到结果集
			rs=pstmt.executeQuery();
			//6.处理结果集
			while(rs.next()) {
				score = rs.getInt(1);
			}
			//7.释放资源
			DBUtil.releaseDB(rs, pstmt, connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return score;
	}
	
	/**
	 * 查询排行
	 * @param count 查询的条数
	 * @return 多条RankVo——》List<RankVo>
	 */
	public List<RankVo> selectRanks(int count) {
		Connection connection =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<RankVo> ranks=null;
		
		try {
			//1.获取连接Connection
			connection = DBUtil.getConnection();
			//2.写sql
			String sql="select username,score,date from user u,rank"+Config.mapID+
					" r where u.id =r.userID order by score desc limit ?";
			ranks=new ArrayList<RankVo>();
			//3.获取PreparedStatement
			pstmt = connection.prepareStatement(sql);
			//4.如果sql中有占位符，需要占位符赋值
			pstmt.setInt(1, count);
			//5.执行查询得到结果集
			rs=pstmt.executeQuery();
			//6.处理结果集
			while(rs.next()) {
				String username = rs.getString(1);
				int score = rs.getInt(2);
				String date=rs.getString(3);
				RankVo rankVo = new RankVo(username, score, date);
				ranks.add(rankVo);
				
			}
			//7.释放资源
			DBUtil.releaseDB(rs, pstmt, connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//8.返回
		return ranks;
	}
}
