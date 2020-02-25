package com.jt.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Set;

import com.jt.util.Config;

/**
 * 食物类
 * @author Zhy
 *
 */
public class Food{
	
	private int row;//所在行
	private int col;//所在列
	
	private Set<Wall> set;
	
	
	public Food(Set<Wall> set) {
		this.set=set;
		repair();
	}
	
	//随机指定行列，生成食物
	public void repair() {	
		row=new Random().nextInt(Config.ROWS);//0~Cofig.Rows-1间的随机数
		col=new Random().nextInt(Config.COLS);
		//如果生成在墙内，则重新制定行列
		synchronized (set) {
			if(Config.isBattle) {
				if(row<(Config.timeWall+3)) {
					System.out.println("食物在毒圈内，重绘食物");
					this.repair();
				}else {
					for(Wall wall:set) {
						if(row==wall.getRow()&&col==wall.getCol()) {
							System.out.println("食物在墙内，重绘食物");
							this.repair();
						}
					}
				}
			}else {
				for(Wall wall:set) {
					if(row==wall.getRow()&&col==wall.getCol()) {
						this.repair();
					}
				}
			}
		}
	}
	
	//绘制食物自身的方法
	public void draw(Graphics g) {
		//设置画笔颜色
//		g.setColor(Color.red);
//		g.fillRect(col*Config.SPAN, row*Config.SPAN,Config.SPAN, Config.SPAN);
		g.drawImage(Config.food, col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN, null);
	}
	
	//获得食物所在的矩形区域Rectangle
	public Rectangle getRect() {
		return new Rectangle(col*Config.SPAN, row*Config.SPAN,Config.SPAN, Config.SPAN);
	}
}
