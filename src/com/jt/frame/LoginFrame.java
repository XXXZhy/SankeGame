package com.jt.frame;

import javax.swing.JFrame;
import com.jt.panel.LoginPanel;
import com.jt.util.ImageUtil;

//继承JFrame
public class LoginFrame extends BaseFrame{
	//创建面板对象
	LoginPanel loginPanle=new LoginPanel(this);
	
	//添加无参构造方法，用于创建窗体
	public LoginFrame() {
		//设置窗体显示位置,前两个参数为窗体左上角坐标，后两个参数为宽度高度
		this.setBounds(300, 50, 706, 500);
		//调用设置窗体信息的方法initFrame()
		initFrame();
		//调用添加组件的方法
		addComponents();
		//设置关闭窗口，程序终止
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		//设置窗体显示，通产在最后进行设置
		this.setVisible(true);
		
	}
	
	//添加组件
	private void addComponents() {
		this.add(loginPanle);
	}

	//程序的入口函数main
	public static void main(String[] args) {
		new LoginFrame();
		//new SnakeFrame();
	}
}
