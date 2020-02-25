package com.jt.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jt.frame.SnakeFrame;
import com.jt.po.Food;
import com.jt.po.Map;
import com.jt.po.Score;
import com.jt.po.Snake;
import com.jt.util.Config;
import com.jt.util.ImageUtil;

//游戏的面板
public class SnakePanel extends JPanel implements KeyListener{
	
	Map map=new Map();//地图对象
	Score score=new Score();//创建分数对象
	Food food=new Food(map.getSet());//创建食物对象
	Snake snake=new Snake(food,score);//创建贪吃蛇对象
	SnakeThread snakeThread=new SnakeThread();//创建贪吃蛇移动线程的对象
	Font font=new Font("Score",Font.BOLD, 30);//Score字体
	Image bg_game=ImageUtil.getImage("img/bg_game.jpg");//背景图片
	
	public SnakePanel() {
		//初始化面板信息
		initPanel();
		//设置键盘事件的监听
		this.addKeyListener(this);
		//启动蛇移动的线程
	}
	public SnakePanel(String game) {
		this();
		//启动蛇移动的线程
		snakeThread.start();
	}
	
	//初始化面板信息的方法
	private void initPanel() {
		//设置位置信息
		this.setBounds(0, 0, 1500, 840);
		//设置背景色
		this.setBackground(Color.yellow);
		
	}
	
	//重写paint（绘制容器）	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//绘制背景图片
		//g.drawImage(bg_game, 0, 0, this);
		g.drawImage(bg_game, 0, 0, getSize().width, getSize().height, this);//图片会自动缩放
		//绘制障碍物
		map.draw(g);
		//调用绘制food方法
		food.draw(g);
		//调用蛇绘制自身的方法，来绘制蛇
		snake.draw(g);
		//设置画笔颜色,画Score
		g.setColor(Color.yellow);
		g.setFont(font);
		g.drawString("Score:"+score.getScore(), 40, 40);
	}

	//创建贪吃蛇的线程类
	class SnakeThread extends Thread{
		
		@Override
		public void run() {
				//蛇持续移动
				while(Config.isLive) {
					//当前线程休息
					try {
						Thread.sleep(Config.speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Config.isRunning) {
						//调用蛇移动的方法
						snake.move(map.getSet());//传障碍物集合
						//重绘
						repaint();	
					}
				}
				if(!Config.isLive&&!Config.isStop) {
					JOptionPane.showMessageDialog(SnakePanel.this, "游戏结束", "消息", JOptionPane.INFORMATION_MESSAGE);
				}
		}
	}
	
	//键入键时调用，键入事件：此事件在输入“字符”的时候生成
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	//键盘按下的时候调用
	@Override
	public void keyPressed(KeyEvent e) {
		//根据按下的键来控制蛇的移动方向		
		snake.dirControl(e);
	}
	//键盘已释放的时候调用
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	

		
	
	
	
}
