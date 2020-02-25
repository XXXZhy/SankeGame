package com.jt.po;

import java.awt.Color;
import java.awt.Graphics;

import com.jt.util.Config;

/**
 * 贪吃蛇分割的节点类
 * @author Zhy
 *
 */
public class Node {
	
	int row;//行
	
	int col;//列
	
	int snakePlayer;//双蛇模式下的 玩家ID

	String dir;//前进方向“u”↑ ,“d”↓,"l"←,“r”→ ...
	
	//下一个节点
	Node next;
	//上一个节点
	Node pre;
	
	public Node(int row, int col, String dir) {
		super();
		this.row = row;
		this.col = col;
		this.dir = dir;
	}

	
	
	
	//绘制节点
	public void draw(Graphics g, int snakePlayer) {
		if(Config.gameName.equals("排位模式")) {
			//g.setColor(Color.blue);
			//如果是蛇头，设置画笔颜色为绿色
			//绘制节点
			if(this.pre==null) {
				g.setColor(Color.GRAY);
				g.fillOval(col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN);
			}else {
				g.drawImage(Config.head1, col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN, null);
			}
			
		}else if(Config.gameName.equals("双蛇模式")) {
			if(snakePlayer==1) {
				g.setColor(Color.cyan);//青色
				//如果是蛇头，设置画笔颜色为深灰色
				if(this.pre==null) {
					g.setColor(Color.DARK_GRAY);
					g.fillOval(col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN);
					
				}else {
					g.drawImage(Config.head1, col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN, null);
				}
			}else if(snakePlayer==2) {
				if(this.pre==null) {
					g.setColor(Color.ORANGE);
					g.fillOval(col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN);
					
				}else {
					g.drawImage(Config.head2, col*Config.SPAN, row*Config.SPAN, Config.SPAN, Config.SPAN, null);
				}
			}
			
			
		}
		
	}

	
	
	
	
	

	
}
