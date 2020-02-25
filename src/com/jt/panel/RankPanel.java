package com.jt.panel;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.jt.dao.RankDao;
import com.jt.util.ImageUtil;
import com.jt.vo.RankVo;

/**
 * 游戏排行界面
 * @author Zhy
 *
 */
public class RankPanel extends JPanel{
	
	Image bg_rank=ImageUtil.getImage("img/bg_rank.jpg");
	Font font =new Font("楷体",Font.BOLD,18);
	JTable table;//表格
	
	public RankPanel() {
		//初始化面板信息
		initPanel();
		//初始化表格并填充数据
		initTable();
		//添加组件
		addComponents();
	}
	//添加组件
	private void addComponents() {
		this.add(table);
	}
	//初始化表格
	private void initTable() {
		RankDao rankDao=new RankDao();
		List<RankVo> ranks=rankDao.selectRanks(4);
		Object[][] rowData=new Object[ranks.size()][4];
		//遍历ranks得到每一个排名，同时完成二维数组赋值的工作
		for(int i=0;i<ranks.size();i++) {
			RankVo vo=ranks.get(i);
			
			String username = vo.getUsername();
			int score = vo.getScore();
			String date = vo.getDate();
			
			rowData[i][0]=i+1;
			rowData[i][1]=username;
			rowData[i][2]=score;
			rowData[i][3]=date;
		}
		
//		Object[][] rowData= {
//				{"1","zhangsan",20,"2019-1-1"},
//				{"1","zhangsan",21,"2019-1-1"},
//				{"1","zhangsan",22,"2019-1-1"},
//				{"1","zhangsan",23,"2019-1-1"}
//		};
		Object[] columnNames= {"排名","姓名","分数","时间"};
		table=new JTable(rowData,columnNames) {
			//重写渲染单元格方法
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				//获取单元格
				Component cell=super.prepareRenderer(renderer, row, column);
				//设置单元格背景透明
				if(cell instanceof JComponent) {
					//1.强制转换为JComponent
					((JComponent)cell).setOpaque(false);
				}
				return cell;
			}
		};
		//设置位置和大小
		table.setBounds(100,180,500,300);
		//设置字体
		table.setFont(font);
		//设置行高
		table.setRowHeight(40);
		//设置是否可用
		table.setEnabled(false);
		//设置网格
		table.setShowGrid(false);
		//设置表格背景透明
		table.setOpaque(false);
		
	}

	//初始化面板信息
	private void initPanel() {
		this.setBounds(0, 0, 700, 500);
		this.setLayout(null);
	}

	
	//重写paintComponent();
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg_rank, 0, 0, this);
	}

	
	
}
