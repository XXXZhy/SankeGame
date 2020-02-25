package com.jt.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jt.dao.UserDao;
import com.jt.util.ImageUtil;

public class RegistPanel extends JPanel implements ActionListener{
	
	Image bg_register;
	JLabel lbl_username,lbl_password1,lbl_password2,lbl_message;//用户名、密码1、密码2的文本
	JButton btn_register;//注册按钮
	JTextField tf_username;//用户名文本框
	JPasswordField pf_password1,pf_password2;//密码框
	
	public RegistPanel() {
		//调用初始化面板信息的方法
		initPanel();
		//初始化组件信息
		initComponents();
		//添加组件
		addComponents();
	}
	
	
	//添加组件
	private void addComponents() {
		this.add(lbl_username);
		this.add(lbl_password1);
		this.add(lbl_password2);
		this.add(tf_username);
		this.add(pf_password1);
		this.add(pf_password2);
		this.add(btn_register);
		this.add(lbl_message);
		
	}
	//初始化组件信息
	private void initComponents() {
		//初始化图片
		bg_register=ImageUtil.getImage("img/bg_login.jpg");
		//初始化文本、文本框、密码框、按钮
		lbl_username=new JLabel("用      户       名：");
		lbl_password1=new JLabel("请 输 入 密 码：");
		lbl_password2=new JLabel("重复输入密码：");
		btn_register=new JButton("确定注册");
		tf_username=new JTextField(16);
		pf_password1=new JPasswordField(16);
		pf_password2=new JPasswordField(16);
		lbl_message=new JLabel("",JLabel.CENTER);//信息居中
		
		//设置上述组件的位置
		lbl_username.setBounds(40, 30, 100, 20);
		lbl_password1.setBounds(40, 70, 100, 20);
		lbl_password2.setBounds(40, 110, 100, 20);
		
		tf_username.setBounds(150, 30, 100, 20);
		pf_password1.setBounds(150, 70, 100, 20);
		pf_password2.setBounds(150, 110, 100, 20);
		
		btn_register.setBounds(100, 160, 100, 40);
		btn_register.addActionListener(this);
		
		lbl_message.setBounds(75,220,150,20);
		
	}
	//调用初始化面板信息的方法
	private void initPanel() {
		//设置面板的位置
		setBounds(0, 0, 300, 300);
		//设置背景色
		this.setBackground(Color.GREEN);
		//设置布局
		this.setLayout(null);
	}
	
	//重写paintComponent();
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg_register, 0, 0, getSize().width, getSize().height, this);//图片会自动缩放
	}

	//注册按钮单击调用此方法
	@Override
	public void actionPerformed(ActionEvent e) {
		//获取用户名文本框的值
		String username = tf_username.getText();
		//获取密码
		String password1 = new String(pf_password1.getPassword()) ;
		String password2 = new String(pf_password2.getPassword()) ;
		
		if(password1.equals(password2)) {//密码相同
			//调用UserDao的register(String username,String password)
			UserDao userDao =new UserDao();
			lbl_message.setText(userDao.register(username, password1));
		}else {
			lbl_message.setText("两次密码不相同！");
		}
		
	}

}
