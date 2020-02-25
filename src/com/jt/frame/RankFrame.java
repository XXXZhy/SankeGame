package com.jt.frame;

import javax.swing.JFrame;

import com.jt.panel.RankPanel;

/**
 * 游戏排行窗体
 * @author Zhy
 *
 */
public class RankFrame extends BaseFrame{
	
	RankPanel rankPanel=new RankPanel();//创建排行面板实例
	//创建窗体
	public RankFrame() {
		//设置窗体显示位置,前两个参数为窗体左上角坐标，后两个参数为宽度高度
		this.setBounds(600, 240, 706, 500);
		//调用初始化窗体的方法
		initFrame();
		//添加组件
		addComponents();
		//设置关闭窗口，程序终止
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		//设置可见
		this.setVisible(true);
	}
	//添加组件
	private void addComponents() {
		//添加游戏面板组件
		this.add(rankPanel);
	}

}
