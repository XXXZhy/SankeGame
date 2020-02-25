package com.jt.frame;

import javax.swing.JFrame;

import com.jt.panel.RegistPanel;
import com.jt.util.ImageUtil;

public class RegistFrame extends JFrame{
	
	RegistPanel registPanel=new RegistPanel();
	
	public RegistFrame(){
		initFrame();
		//添加组件
		addComponents();
		//设置可见
		this.setVisible(true);
	}
	
	public void initFrame() {
		//设置窗体显示位置,前两个参数为窗体左上角坐标，后两个参数为宽度高度
		this.setBounds(500, 180, 300, 300);
		//设置标题
		this.setTitle("贪吃蛇账号注册");
		//设置图标
		this.setIconImage(ImageUtil.getImage("img/logo.png"));
		//设置布局，不适用布局
		this.setLayout(null);
		//设置窗体不可调节大小
		this.setResizable(false);
	}

	
	private void addComponents() {
		this.add(registPanel);
	}

}
