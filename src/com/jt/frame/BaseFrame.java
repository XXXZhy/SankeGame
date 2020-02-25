package com.jt.frame;

import javax.swing.JFrame;

import com.jt.util.ImageUtil;

/**
 * Frame的模板
 * @author Zhy
 *
 */
public class BaseFrame extends JFrame{
	
	//设置窗体信息
	public void initFrame() {
		//设置窗体显示位置,前两个参数为窗体左上角坐标，后两个参数为宽度高度
		//this.setBounds(300, 50, 706, 500);
		//设置标题
		this.setTitle("贪吃蛇");
		//设置图标
		this.setIconImage(ImageUtil.getImage("img/logo.png"));
		//设置布局，不适用布局
		this.setLayout(null);
		//设置窗体不可调节大小
		this.setResizable(false);
		//设置关闭窗口，程序终止
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
