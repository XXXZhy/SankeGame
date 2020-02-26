package com.jt.po;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.jt.util.Config;

public class Map {
	//CopyOnWriteArraySet多线程安全!
	Set<Wall> set=new CopyOnWriteArraySet<Wall>();
	
	//构造函数：初始化墙集合Set
	public Map() {
		switch (Config.mapID) {
		case 0:
			mapOne();
			break;
		case 1:
			mapTwo();
			break;
		case 2:
			mapThree();
			break;
		case 3:
			mapFour();
		default:
			break;
		}
	}
	
	//根据Config.mapID画地图
	public void draw(Graphics g) {
		switch (Config.mapID) {
		case 0:
			mapOne();
			drawMap(g);
			break;
		case 1:
			mapTwo();
			drawMap(g);
			break;
		case 2:
			mapThree();
			drawMap(g);
			break;
		case 3:
			mapFour();
			drawMap(g);
		default:
			break;
		}
	}
	
	//把Set集合里的强画出来
	private void drawMap(Graphics g) {
		for(Wall wall:set) {
			//设置障碍物颜色为黑色
//			g.setColor(Color.black);
//			g.fillRect(wall.getCol()*Config.SPAN,wall.getRow()*Config.SPAN, Config.SPAN, Config.SPAN);
			g.drawImage(Config.wall, wall.getCol()*Config.SPAN, wall.getRow()*Config.SPAN, Config.SPAN, Config.SPAN, null);
		}
	}
	
	
	
	//地图一
	private void mapOne() {
		//清空集合
		set.clear();
		if(Config.isBattle) {mapFour();};
		
		//Wall wall1 = new Wall(Config.ROWS-1, Config.COLS-1);
		//set.add(wall1);
	}
	
	//地图二
	private void mapTwo() {
		//清空集合
		set.clear();
		
		for(int i=15;i<60;i++) {
			Wall wall1 = new Wall(10, i );
			Wall wall2 = new Wall(20, i );
			Wall wall3 = new Wall(30, i );
			set.add(wall1);
			set.add(wall2);
			set.add(wall3);
		}
		
		if(Config.isBattle) {mapFour();};
	}
	
	//地图三
	private void mapThree() {
		//清空集合
		set.clear();
		
		for(int i=10;i<30;i++) {
			Wall wall1 = new Wall(i, 7 );
			Wall wall2 = new Wall(i, 17 );
			Wall wall3 = new Wall(i, 27 );
			Wall wall4 = new Wall(i, 37 );
			Wall wall5 = new Wall(i, 47 );
			Wall wall6 = new Wall(i, 57 );
			Wall wall7 = new Wall(i, 67 );
			set.add(wall1);
			set.add(wall2);
			set.add(wall3);
			set.add(wall4);
			set.add(wall5);
			set.add(wall6);
			set.add(wall7);
		}
		
		if(Config.isBattle) {mapFour();};
		
	}
	
	
	
	//大逃杀
	
	private void mapFour() {
		//上部分的墙
		for(int i=0;i<Config.timeWall;i++) {
			for(int j=0;j<Config.COLS;j++) {
				Wall wall1 = new Wall(i, j);
				set.add(wall1);
			}
		}
	}
	
	
	
	 public Set<Wall> getSet() {
			return set;
		
	}

	public void setSet(Set<Wall> set) {
		this.set = set;
	}
	
	
	
}
