package com.jt.po;

import java.awt.Color;
import java.awt.Graphics;

import com.jt.util.Config;

public class Wall {
	
	private int row;//所在行
	private int col;//所在列
	
	public Wall(int row,int col) {
		this.row=row;
		this.col=col;
		//this.draw(g);
	}
	
	//绘制
//	public void draw(Graphics g) {
//		//设置障碍物颜色为黑色
//		g.setColor(Color.black);
//		g.fillRect(col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN);
//	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
}
