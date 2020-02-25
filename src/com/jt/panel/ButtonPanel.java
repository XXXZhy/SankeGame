 package com.jt.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.Thread.State;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jt.dao.RankDao;
import com.jt.frame.RankFrame;
import com.jt.frame.SnakeFrame;
import com.jt.util.Config;

public class ButtonPanel extends JPanel implements ActionListener,ItemListener{
	JComboBox<String> btn_map;//地图下拉框
	JButton btn_pause_continue;//暂停继续按钮
	JButton btn_restart;//重新开始
	JButton btn_rank;//排行榜按钮
	JButton btn_battle;//大逃杀
	JLabel lbl_score;//排位分数显示
	JTextArea tarea_scorePlayer1,tarea_scorePlayer2;//排位分数显示,玩家1赢局,玩家2赢局
	SnakeFrame snakeFrame;
	SnakePanel snakePanel;
	RankDao rankDao=new RankDao();
	boolean isPause=false;//是否暂停
	String gameName;//当先游戏模式

	
	public ButtonPanel(SnakeFrame snakeFrame,SnakePanel snakePanle,String gameName) {
		this.snakeFrame=snakeFrame;
		this.snakePanel=snakePanle;
		this.gameName=gameName;
		Config.maxScore=rankDao.selectScore(Config.userID);
		//初始化面板
		this.initPanle();
		//初始化按钮组件信息
		this.initComponents();
		//添加组件
		this.addComponents();
		//回车默认为登录按钮
		snakeFrame.getRootPane().setDefaultButton(btn_pause_continue);
	}
	

	//添加按钮组织
	private void addComponents() {
		add(btn_map);
		add(btn_battle);
		add(btn_pause_continue);
		add(btn_restart);
		if(gameName.equals("排位模式")) {
			add(btn_rank);
			add(lbl_score);
		}else if(gameName.equals("双蛇模式")) {
			add(tarea_scorePlayer1);
			add(tarea_scorePlayer2);
		}
		
	}
	
	//初始化按钮组件
	private void initComponents() {
		//初始按钮和文本框
		btn_map=new JComboBox<String>(new String[] {"地图一","地图二","地图三"});
		btn_pause_continue=new JButton("暂停游戏");
		btn_restart=new JButton("重新开始");
		btn_rank=new JButton("游戏排行");
		btn_battle=new JButton("大逃杀未开启");
		lbl_score=new JLabel("您的最高分数："+Config.maxScore,JLabel.CENTER);
		tarea_scorePlayer1=new JTextArea("玩家1:\n            "+Config.scorePlayer1,2,8);
		tarea_scorePlayer2=new JTextArea("玩家2:\n            "+Config.scorePlayer2,2,8);
		tarea_scorePlayer1.setFont(new Font("玩家1分数",Font.BOLD,18));;
		tarea_scorePlayer2.setFont(new Font("玩家2分数",Font.BOLD,18));;
		tarea_scorePlayer1.setEditable(false);//多行文本不可编辑
		tarea_scorePlayer2.setEditable(false);//多行文本不可编辑
		
		
		
		//按钮增加监听
		btn_map.addItemListener(this);
		btn_pause_continue.addActionListener(this);
		btn_restart.addActionListener(this);
		btn_rank.addActionListener(this);
		btn_battle.addActionListener(this);
	}

	//初始化面板的方法
	private void initPanle() {
		setBounds(1500, 0, 150, 900);
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		
	}
	//地图下拉列表 事件监听
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==btn_map) {
			if(btn_map.getSelectedIndex()==0) {
				Config.mapID=0;//切换地图0
				Config.maxScore=rankDao.selectScore(Config.userID);
				gameRestart();//重新开始游戏
			}else if(btn_map.getSelectedIndex()==1) {
				Config.mapID=1;//切换地图1
				Config.maxScore=rankDao.selectScore(Config.userID);
				gameRestart();//重新开始游戏
			}else if(btn_map.getSelectedIndex()==2) {
				Config.mapID=2;//切换地图2
				Config.maxScore=rankDao.selectScore(Config.userID);
				gameRestart();//重新开始游戏
			}
			

		}
		
	}
	
	//按钮事件监听
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_pause_continue) {
			if(!isPause) {
				btn_pause_continue.setText("继续游戏");
				gamePause();
				isPause=true;
			}else if(isPause) {
				btn_pause_continue.setText("暂停游戏");
				gameContinue();
				isPause=false;
			}
		}else if(e.getSource()==btn_restart) {
			gameRestart();
		}else if(e.getSource()==btn_rank) {
			gameRank();
		}else if(e.getSource()==btn_battle) {
			if(Config.isBattle) {//关闭大逃杀
				Config.isBattle=false;
				btn_battle.setText("大逃杀未开启");
				gameRestart();
			}else if(!Config.isBattle) {//大逃杀开启
				Config.isBattle=true;
				btn_battle.setText("大逃杀已开启");
				gameRestart();
			}
			
			
		}
	}
	
	
	//游戏排行
	private void gameRank() {
		//暂停游戏
		gamePause();
		btn_pause_continue.setText("继续游戏");
		//打开游戏排行的窗体
		new RankFrame();
	}
	
	//重新开始
	private void gameRestart() {
		isPause=false;
		btn_pause_continue.setText("暂停游戏");
		if(gameName.equals("排位模式")) {
			//终止SanakeThread线程
			try {
				Config.isStop=true;
				Config.isLive=false;
				Thread.sleep(120);//等待SnakeThread终止
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(snakePanel.snakeThread.getState()==State.TERMINATED) {//判断线程是否已经终止
				Config.speed=100;//重置蛇的速度
				Config.isRunning=true;//取消暂停
				Config.isLive=true;//复活蛇
				Config.isStop=false;//激活线程
				Config.timeWall=0;//重置大逃杀
				SnakePanel snakePanel2=new SnakePanel("排位模式");
				snakeFrame.remove(snakePanel);
				snakeFrame.add(snakePanel2);
				snakeFrame.repaint();
				//使游戏面板获取焦点
				this.snakePanel=snakePanel2;
				snakePanel.setFocusable(true);
				snakePanel.requestFocus();
				//更新最高分
				lbl_score.setText("您的最高分数："+Config.maxScore);
			}
		}else if(gameName.equals("双蛇模式")) {
			DoubleSnakePanel doubleSnakePanel=(DoubleSnakePanel) snakePanel;
			//终止SanakeThread线程
			try {
				Config.isStop=true;
				Config.isLivePlayer1=false;
				Config.isLivePlayer2=false;
				Thread.sleep(120);//等待SnakeThread终止
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(doubleSnakePanel.snakeThread1.getState()==State.TERMINATED&&doubleSnakePanel.snakeThread2.getState()==State.TERMINATED) {//判断线程是否已经终止
				Config.speedPlayer1=100;//重置蛇的速度
				Config.speedPlayer2=100;//重置蛇的速度
				Config.isRunning=true;//取消暂停
				Config.isLivePlayer1=true;//复活蛇1
				Config.isLivePlayer2=true;//复活蛇2
				Config.isStop=false;//激活线程
				Config.isGameOver=false;//重启游戏
				Config.timeWall=0;//重置大逃杀
				DoubleSnakePanel doubleSnakePanel2=new DoubleSnakePanel();
				snakeFrame.remove(doubleSnakePanel);
				snakeFrame.add(doubleSnakePanel2);
				snakeFrame.repaint();
				//使游戏面板获取焦点
				doubleSnakePanel=doubleSnakePanel2;
				doubleSnakePanel.setFocusable(true);
				doubleSnakePanel.requestFocus();
				//更新双方分数
				tarea_scorePlayer1.setText("玩家1:\n            "+Config.scorePlayer1);
				tarea_scorePlayer2.setText("玩家2:\n            "+Config.scorePlayer2);
			}

		}
		
		
	}
	
	//继续游戏
	private void gameContinue() {
		Config.isRunning=true;
		//使游戏面板获取焦点
		snakePanel.setFocusable(true);
		snakePanel.requestFocus();
	}
	
	//暂停游戏
	private void gamePause() {
		Config.isRunning=false;
	}

	
	
	
	
}
