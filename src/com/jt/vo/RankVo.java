package com.jt.vo;
/**
 * 游戏排行的视图
 * @author Zhy
 *
 */
public class RankVo {
	
	private String username;
	
	private int score;
	
	private String date;
	
	public RankVo() {
		super();
	}
	
	public RankVo(String username, int score, String date) {
		super();
		this.username = username;
		this.score = score;
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "RankVo [username=" + username + ", score=" + score + ", date=" + date + "]";
	}
	
	
	
}


