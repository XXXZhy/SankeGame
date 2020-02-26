package com.jt.util;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

/**
 * 配置信息
 * @author Zhy
 *
 */
public class Config {
	public static final int ROWS=42;//行
	public static final int COLS=75;//列
	public static final int SPAN=20;//间距
	//定义方向的常量
	public static final String U="u";//上
	public static final String D="d";//下
	public static final String L="l";//左
	public static final String R="r";//右
	//蛇头图片
	public static Image head1 = ImageUtil.getImage("img/head1.png");
	public static Image head2 = ImageUtil.getImage("img/head2.png");
	public static Image wall = ImageUtil.getImage("img/wall.png");
	public static Image food = ImageUtil.getImage("img/food.png");
	
	public static boolean isLive=true;//蛇死亡的标识
	public static boolean isRunning=true;//标记暂停
	public static boolean isStop=false;//标记是否手动停止线程
	public static int speed=100;//默认速度
	public static int userID=0;//登录用户的ID
	public static int maxScore=0;//当前用户的历史最高分数
	public static boolean isAddHead=true;//重置方向后是否添加了一个头结点
	public static int mapID=0;//地图默认ID=0;
	
	//双蛇模式配置
	public static String gameName="排位模式";
	public static boolean isLivePlayer1=true;//玩家1死亡标志
	public static boolean isLivePlayer2=true;//玩家2死亡标志
	public static boolean isGameOver=false;//双蛇游戏结束
	public static boolean isAddHeadPlayer1=true;//重置方向后是否添加了一个头结点
	public static boolean isAddHeadPlayer2=true;//重置方向后是否添加了一个头结点
	public static int scorePlayer1=0;//玩家1胜利场数
	public static int scorePlayer2=0;//玩家2胜利场数
	public static int speedPlayer1=100;//玩家1默认速度
	public static int speedPlayer2=100;//玩家2默认速度
	
	//大逃杀
	public static boolean isBattle=false;
	public static int timeWall=0;
}
