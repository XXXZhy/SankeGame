package com.jt.po;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Set;

import com.jt.util.Config;

public class DoubleSnake {

	//蛇头
	Node head;
	//蛇身
	Node body;
	//蛇尾
	Node tail;
	//当前食物
	Food food;	
	//创建分数对象
	Score score=new Score();
	
	//障碍物集合
	Set<Wall> set;
	
	int snakePlayer;//标记当前玩家ID
	DoubleSnake foeSnake;//敌人的蛇
	
	//创建贪吃蛇对象
	public DoubleSnake(Food food,int snakePlayer) {
		this.food=food;
		this.snakePlayer=snakePlayer;
		//实例化蛇头、蛇身、蛇尾的属性
		if(snakePlayer==1) {
			head=new Node(37, 71, Config.L);
			body=new Node(37, 72, Config.L);
			tail=new Node(37, 73, Config.L);
		}else if(snakePlayer==2) {
			head=new Node(4, 4, Config.R);
			body=new Node(4, 3, Config.R);
			tail=new Node(4, 2, Config.R);
		}
		
		//设置节点间的关系
		head.next=body;
		body.pre=head;
		body.next=tail;
		tail.pre=body;
		head.pre=null;
		
	}
	//绘制蛇的方法——》其实就是绘制蛇的每一个节点
	public void draw(Graphics g, int snakePlayer) {
		//得到蛇的每一个节点，并绘制
		for(Node node=head;node!=null;node=node.next) {
			//绘制节点——》调用节点类中绘制节点的方法
			node.draw(g,snakePlayer);
		}
	}
	//贪吃蛇移动方法
	public void move(Set<Wall> set1, DoubleSnake foeSnake) {
		this.set=set1;
		this.foeSnake=foeSnake;
		
		/*
		 * 1.添加头结点
		 * 2.判断是否吃到食物
		 * 3.如果没吃到食物，则移除尾结点
		 * 4.判断蛇是否死亡
		 */
		addHead();
		if(!isEat()) {
			//移除尾结点
			removeTail();
		}
		//判断蛇是否死亡
		checkDeath();
		
	}
	//出现Exception in thread "Thread-7" java.util.ConcurrentModificationException异常   已经解决：Set集合用CopyOnWriteArraySet多线程安全!
	private void checkDeath() {
		//越界
		if(head.row<0||head.row>Config.ROWS-1||head.col<0||head.col>Config.COLS-1) {
			//越界死亡
			if(snakePlayer==1) {
				System.out.println("1越界死亡");
				Config.isLivePlayer1=false;
				Config.isGameOver=true;
			} 
			else if(snakePlayer==2) {
				System.out.println("2越界死亡");
				Config.isLivePlayer2=false;
				Config.isGameOver=true;
			} 
		}
		
		//遍历对方蛇的节点，若果蛇头相撞则分数高获胜，撞道敌人身体则失败
		
		for(Node n=foeSnake.head;n!=null&&Config.isGameOver==false;n=n.next) {
			//判断
			if(n.row==head.row&&n.col==head.col) {
				if(n.pre==null) {
					System.out.println("蛇头相撞了！！"+snakePlayer+"对手分数："+foeSnake.score.getScore()+"我的分数："+score.getScore());
					//蛇头相撞
					compareScore();//比较分数判断谁赢
				}else {
					///撞敌人蛇身
					if(snakePlayer==1) {
						Config.isLivePlayer1=false;
						Config.isGameOver=true;
						System.out.println("1蛇身死亡"+n.row+"  "+n.col);
					} 
					else if(snakePlayer==2) {
						Config.isLivePlayer2=false;
						Config.isGameOver=true;
						System.out.println("2蛇身死亡"+n.row+"  "+n.col);
					} 
					break;
				}
			}
		}
		
		//java.util.ConcurrentModificationException异常，解决不了	 已经解决：Set集合用CopyOnWriteArraySet多线程安全!
		//蛇撞障碍物则死亡
			for(Wall wall:set) {
				if(wall.getRow()==head.row&&wall.getCol()==head.col) {
					//蛇死亡
					System.out.println("障碍死亡");
					if(snakePlayer==1) {
						Config.isLivePlayer1=false;
						Config.isGameOver=true;
						System.out.println("1障碍死亡");
					} 
					else if(snakePlayer==2) {
						Config.isLivePlayer2=false;
						Config.isGameOver=true;
						System.out.println("2障碍死亡");
					} 
					break;
				}
			}
		
		//当墙生成在最后三行时，谁分数高谁获胜
		if(Config.timeWall==Config.ROWS-4) {
			compareScore();//比较分数判断谁赢
		}
		
		/*
		 * 如果死亡，保存成绩重新开始
		 * 
		 */
		
	}
	//比较分数判断谁赢
	private void compareScore() {
		if(snakePlayer==1) {
			if(foeSnake.score.getScore()>score.getScore()) {
				Config.isLivePlayer1=false;
				Config.isGameOver=true;
			}else if(foeSnake.score.getScore()<score.getScore()) {
				Config.isLivePlayer2=false;
				Config.isGameOver=true;
			}else {
				Config.isLivePlayer1=false;
				Config.isLivePlayer2=false;
				Config.isGameOver=true;
			}
			
		} 
		else if(snakePlayer==2) {
			if(foeSnake.score.getScore()>score.getScore()) {
				Config.isLivePlayer2=false;
				Config.isGameOver=true;
			}else if(foeSnake.score.getScore()<score.getScore()) {
				Config.isLivePlayer1=false;
				Config.isGameOver=true;
			}else {
				Config.isLivePlayer1=false;
				Config.isLivePlayer2=false;
				Config.isGameOver=true;
			}
		}
	}
	
	//移除尾结点
	private void removeTail() {
		tail.pre.next=null;//将倒数第二个节点的下一个节点设Null
		tail=tail.pre;//倒数第二个成为新的尾结点
	}
	//判断是否吃到食物——》返回值为Boolean代表是否吃到食物，true吃到false未吃到
	public boolean isEat() {
		//获得蛇头所在的矩形区域
		//获得食物所在的矩形区域
		//判断这两个矩形区域是否交叉，如果交叉代表吃到，否则未吃到
		if(getRect().intersects(food.getRect())) {//吃到了食物
			//重新定位食物
			food.repair();
			//分数+1
			score.setScore(score.getScore()+1);
			//大逃杀模式Wall+1
			if(Config.isBattle) {Config.timeWall++;}
			
			return true;
		}
		
		return false;
	}
	
	//得到蛇头所在的矩形区域
	public Rectangle getRect() {
		return new Rectangle(head.col*Config.SPAN,head.row*Config.SPAN,Config.SPAN,Config.SPAN);
	}
	//添加蛇头
	private void addHead() {
		//创建新的节点
		Node node=null;
		//根据蛇头前进的方向不同，创建的新节点是不同的
		switch(head.dir) {
		case Config.U:
			//蛇头前进的方向为↑，——》行——》头结点的行-1  列——》头结点的列
			node=new Node(head.row-1, head.col, head.dir);
			break;
		case Config.D:
			//蛇头前进的方向为↓，——》行——》头结点的行+1  列——》头结点的列
			node=new Node(head.row+1, head.col, head.dir);
			break;
		case Config.L:
			//蛇头前进的方向为←，——》行——》头结点的行	列——》头结点的列-1
			node=new Node(head.row, head.col-1, head.dir);
			break;
		case Config.R:
			//蛇头前进的方向为→，——》行——》头结点的行	列——》头结点的列+1
			node=new Node(head.row, head.col+1, head.dir);
			break;	
		default:
			break;
		}
		//设置新节点与源节点的关系
		node.next=head;
		head.pre=node;
		//设置新节点为蛇头
		head=node;
		if(snakePlayer==1) {
			Config.isAddHeadPlayer1=true;
		}else if(snakePlayer==2) {
			Config.isAddHeadPlayer2=true;
		}
	}
	
	//控制蛇的移动方向(蛇头)
	public void dirControl(KeyEvent e,int snakePlayer) {
		if(Config.isAddHeadPlayer1&&snakePlayer==1) {
			//判断按键
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP://↑键
				//↑——》如果蛇头前进的方向不为↓，将蛇头前进的方向设置为↑
				if(!head.dir.equals(Config.D)) {
					//设置蛇头的方向为↑
					head.dir=Config.U;
				}
				break;
			case KeyEvent.VK_DOWN://↓键
				//↓——》如果蛇头前进的方向不为↑，将蛇头前进的方向设置为↓
				if(!head.dir.equals(Config.U)) {
					//设置蛇头的方向为下
					head.dir=Config.D;
				}
				break;
			case KeyEvent.VK_LEFT://←键
				//←——》如果蛇头前进的方向不为→，将蛇头前进的方向设置为←
				if(!head.dir.equals(Config.R)) {
					//设置蛇头的方向为←
					head.dir=Config.L;
				}
				break;
			case KeyEvent.VK_RIGHT://→键
				//→——》如果蛇头前进的方向不为←，将蛇头前进的方向设置为→
				if(!head.dir.equals(Config.L)) {
					//设置蛇头的方向为↑
					head.dir=Config.R;
				}
				break;
			default:
				break;
			}
			Config.isAddHeadPlayer1=false;
		}else if(Config.isAddHeadPlayer2&&snakePlayer==2) {
			//判断按键
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W://↑键
				//↑——》如果蛇头前进的方向不为↓，将蛇头前进的方向设置为↑
				if(!head.dir.equals(Config.D)) {
					//设置蛇头的方向为↑
					head.dir=Config.U;
				}
				break;
			case KeyEvent.VK_S://↓键
				//↓——》如果蛇头前进的方向不为↑，将蛇头前进的方向设置为↓
				if(!head.dir.equals(Config.U)) {
					//设置蛇头的方向为下
					head.dir=Config.D;
				}
				break;
			case KeyEvent.VK_A://←键
				//←——》如果蛇头前进的方向不为→，将蛇头前进的方向设置为←
				if(!head.dir.equals(Config.R)) {
					//设置蛇头的方向为←
					head.dir=Config.L;
				}
				break;
			case KeyEvent.VK_D://→键
				//→——》如果蛇头前进的方向不为←，将蛇头前进的方向设置为→
				if(!head.dir.equals(Config.L)) {
					//设置蛇头的方向为↑
					head.dir=Config.R;
				}
				break;
			default:
				break;
			}
			Config.isAddHeadPlayer2=false;
		}
		
	}
	
	
	
	
	
}
