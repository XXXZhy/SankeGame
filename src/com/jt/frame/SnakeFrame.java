package com.jt.frame;

import javax.swing.JFrame;

import com.jt.panel.ButtonPanel;
import com.jt.panel.DoubleSnakePanel;
import com.jt.panel.SnakePanel;

public class SnakeFrame extends BaseFrame{
	
	SnakePanel snakePanle;
	ButtonPanel buttonPanel;
	//用户创建游戏界面的窗体
	public SnakeFrame(String gameName) {
		if(gameName.equals("双蛇模式")) {
			snakePanle=new DoubleSnakePanel();
			buttonPanel=new ButtonPanel(this,snakePanle,gameName);
		}else if(gameName.equals("排位模式")) {
			snakePanle=new SnakePanel("排位模式");
			buttonPanel=new ButtonPanel(this,snakePanle,gameName);
		}
		//设置窗体显示位置,前两个参数为窗体左上角坐标，后两个参数为宽度高度
		this.setBounds(100, 50, 1656, 870);
		//设置窗体信息
		initFrame();
		//添加面板组件
		addComponents();
		//设置关闭窗口，程序终止
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		//设置窗体显示，通产在最后进行设置
		this.setVisible(true);
	}

	private void addComponents() {
		add(snakePanle);
		add(buttonPanel);
		
		//使游戏面板获取焦点
		snakePanle.setFocusable(true);
		snakePanle.requestFocus();
		
		
	}
}
