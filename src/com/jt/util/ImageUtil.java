package com.jt.util;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 图形工具类
 * @author Zhy
 *
 */
public class ImageUtil {
	
	//根据图片路径获取Image
	public static Image getImage(String fileName) {
		return getImageIcon(fileName).getImage();
	}
	//根据图片路径获取ImageIcon
	public static ImageIcon getImageIcon(String fileNmae) {
		return new ImageIcon(fileNmae);
	}
	
}
