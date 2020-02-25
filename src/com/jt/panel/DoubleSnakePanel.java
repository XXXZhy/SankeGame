package com.jt.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jt.po.DoubleSnake;
import com.jt.po.Food;
import com.jt.po.Map;
import com.jt.po.Wall;
import com.jt.util.Config;
import com.jt.util.ImageUtil;

//游戏的面板
public class DoubleSnakePanel extends SnakePanel{
	
	Graphics g=this.getGraphics();
	
	DoubleSnake snake1=new DoubleSnake(food,1);//创建玩家1的蛇
	DoubleSnake snake2=new DoubleSnake(food,2);//创建玩家2的蛇
	
	SnakeThread1 snakeThread1=new SnakeThread1();//创建贪吃蛇移动线程的对象
	SnakeThread2 snakeThread2=new SnakeThread2();//创建贪吃蛇移动线程的对象
	Image bg_game=ImageUtil.getImage("img/bg_game.jpg");//背景图片
	
	
	public DoubleSnakePanel() {
		super();
		//启动蛇移动的线程
		snakeThread1.start();
		snakeThread2.start();
	}
	
	
	//重写paint（绘制容器）	
	@Override
	public void paint(Graphics g) {
		super.paint(g);		
		//绘制背景图片
		g.drawImage(bg_game, 0, 0, getSize().width, getSize().height, this);//图片会自动缩放
		//绘制障碍物
		map.draw(g);
		//调用绘制food方法
		food.draw(g);
		//调用蛇绘制自身的方法，来绘制蛇
		snake1.draw(g,1);
		snake2.draw(g,2);
		
	}
	
	
	//创建贪吃蛇的线程类1
	class SnakeThread1 extends Thread{
		
		@Override
		public void run() {
				//蛇持续移动
				while(Config.isLivePlayer1&&!Config.isGameOver) {
					//当前线程休息
					try {
						Thread.sleep(Config.speedPlayer1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Config.isRunning) {
						//调用蛇移动的方法
						synchronized (Map.class) {
							snake1.move(map.getSet(),snake2);//传障碍物集合
							//重绘
							repaint();	
						}
						
					}
				}
				if(!Config.isLivePlayer1&&!Config.isStop) {
					JOptionPane.showMessageDialog(DoubleSnakePanel.this, "玩家2  获胜！", "消息", JOptionPane.INFORMATION_MESSAGE);
					Config.scorePlayer2++;//玩家2分数+1
				}
					
		}
	}
	
	//创建贪吃蛇的线程类2
	class SnakeThread2 extends Thread{
		
		@Override
		public void run() {
				//蛇持续移动
				while(Config.isLivePlayer2&&!Config.isGameOver) {
					//当前线程休息
					try {
						Thread.sleep(Config.speedPlayer2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Config.isRunning) {
						synchronized (Map.class) {
							//调用蛇移动的方法
							snake2.move(map.getSet(),snake1);//传障碍物集合
							//重绘
							repaint();		
						}
						
					}
				}
				if(!Config.isLivePlayer2&&!Config.isStop) {
					JOptionPane.showMessageDialog(DoubleSnakePanel.this, "玩家1  获胜！", "消息", JOptionPane.INFORMATION_MESSAGE);
					Config.scorePlayer1++;//玩家1分数+1
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
		if(e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_RIGHT) {
			snake1.dirControl(e,1);
		}else if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_D) {
			snake2.dirControl(e,2);
		}
		
		if(e.getKeyCode()==96) {//96数字小键盘0，玩家速度变45
			Config.speedPlayer1=45;
		}
		if(e.getKeyCode()==32) {//32空格，玩家速度变45
			Config.speedPlayer2=45;
		}
	}	
	//键盘已释放的时候调用
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==96) {//速度恢复
			Config.speedPlayer1=100;
		} 
		if(e.getKeyCode()==32) {//32空格，玩家速度100
			Config.speedPlayer2=100;
		}
	}
	

		
	
	
	
}
