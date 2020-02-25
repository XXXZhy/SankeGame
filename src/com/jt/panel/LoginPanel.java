package com.jt.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jt.dao.UserDao;
import com.jt.frame.RegistFrame;
import com.jt.frame.SnakeFrame;
import com.jt.util.Config;
import com.jt.util.ImageUtil;
import com.jt.vo.UserVo;

//继承JPanel
public class LoginPanel extends JPanel implements ActionListener{
	
	//定义需要的组件属性
	Image bg_login;
	JLabel lbl_username,lbl_password;//用户名、密码的文本
	JTextField tf_username;//用户名文本框
	JPasswordField pf_password;//密码框
	JButton btn_login,btn_register,btn_doubleSnake;//登录、注册、双蛇按钮
	JFrame loginFrame;
	UserVo user;//登录的用户实例	
	
	//创建面板对象的构造方法
	public LoginPanel(JFrame loginFrame) {
		
		this.loginFrame=loginFrame;
		//调用初始化面板信息的方法
		initPanel();
		//初始化组件信息
		initComponents();
		//添加组件
		addComponents();
		//回车默认为登录按钮
		loginFrame.getRootPane().setDefaultButton(btn_login);
	}
	//添加组件
	private void addComponents() {
		this.add(lbl_password);
		this.add(lbl_username);
		this.add(tf_username);
		this.add(pf_password);
		this.add(btn_login);
		this.add(btn_register);
		this.add(btn_doubleSnake);
	}
	//初始化组件
	private void initComponents() {
		//初始化图片
		bg_login=ImageUtil.getImage("img/bg_login.jpg");
		//初始化文本、文本框、密码框、按钮
		lbl_username=new JLabel("用户名");
		lbl_password=new JLabel("密	码");
		tf_username=new JTextField(16);//设置文本框的宽度
		pf_password=new JPasswordField(16);//设置密码的宽度
		btn_login=new JButton("登录");
		btn_register=new JButton("注册");
		btn_doubleSnake=new JButton("双蛇模式");
		
		//设置上述组件的位置
		lbl_username.setBounds(500, 200, 50, 50);
		lbl_password.setBounds(500, 250, 50, 50);
		
		tf_username.setBounds(550, 215, 100, 20);
		pf_password.setBounds(550, 265, 100, 20);
		
		btn_login.setBounds(550, 300, 100, 30);
		btn_register.setBounds(550, 340, 100, 30);
		btn_doubleSnake.setBounds(550,380,100,30);
		
		btn_login.addActionListener(this);
		btn_register.addActionListener(this);
		btn_doubleSnake.addActionListener(this);
		
		
	}

	//初始化面板信息
	private void initPanel() {
		//设置面板的位置
		this.setBounds(0, 0, 700, 500);
		//设置背景色
		this.setBackground(Color.GREEN);
		//设置布局
		this.setLayout(null);	
	}
	
	//重写paintComponent方法
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//绘制背景颜色
		g.drawImage(bg_login, 0, 0, this);//图片不被缩放
		//g.drawImage(bg_login, 0, 0, getSize().width, getSize().height, this);//图片会自动缩放
	}
	
	//按钮单击调用此方法
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_login) {
			//获取用户名文本框的值
			String username = tf_username.getText();
			//获取密码
			String password = new String(pf_password.getPassword()) ;
			//调用UserDao的login(String username,String password)
			UserDao userDao =new UserDao();
			user = userDao.login(username, password);
			if(user!=null) {
				//登录成功
				JOptionPane.showMessageDialog(this, "登录成功");
				//关闭登录的窗体
				loginFrame.dispose();
				//打开游戏界面的窗体
				Config.userID=user.getId();
				new SnakeFrame("排位模式");//普通模式
				Config.gameName="排位模式";
			}else {
				//登录失败
				
				//重置文本框和密码框
				tf_username.setText(null);
				pf_password.setText(null);
				JOptionPane.showMessageDialog(this, "登录失败");
				
			}
		}else if(e.getSource()==btn_register) {
			new RegistFrame();
		}else if(e.getSource()==btn_doubleSnake) {
			//关闭登录的窗体
			loginFrame.dispose();
			//打开游戏界面的窗体
			new SnakeFrame("双蛇模式");
			Config.gameName="双蛇模式";
		}
		
		
	}
}


