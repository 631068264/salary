package com.web.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

//生产验证码
public class CodeUtils {
	private final static int width = 120;
	private final static int height = 30;
	private static Random random = new Random();

	public static String code;
	public static ByteArrayInputStream imageStream;

	public static void createImage() {
		// 创建图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 往图像写数据 得到图形
		Graphics g = image.getGraphics();
		// 设置背景色
		setBackGround(g);
		// 设置边框
		setBorder(g);
		// 画干扰线
		drawRandomLine(g);
		// 写随机数
		CodeUtils.code = drawRandomNum((Graphics2D) g);
		// 流转换
		CodeUtils.imageStream = convertImageToStream(image);
	}

	/**
	 * 图转换成流
	 * 
	 * @param image
	 * @return
	 */
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {

		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
		try {
			jpeg.encode(image);
			byte[] bts = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 
	 * 验证码写到图上
	 * 
	 * @param g
	 * @return
	 */
	private static String drawRandomNum(Graphics2D g) {
		// 字体样式
		g.setColor(new Color(20 + random.nextInt(110), 100 + random.nextInt(110), 50 + random
				.nextInt(110)));
		g.setFont(new Font("宋体", Font.BOLD, 20));

		String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();

		int x = 5;
		int count = 4;// 验证码个数
		for (int i = 0; i < count; i++) {//
			int degree = random.nextInt() % 30;
			// 设置验证码
			String ch = String.valueOf(code.charAt(random.nextInt(code.length()))) + "";

			sb.append(ch);
			// 设置旋转弧度 设置了弧度会叠加
			g.rotate(degree * Math.PI / 180, x, 20);
			g.drawString(ch, x, 20);
			g.rotate(-degree * Math.PI / 180, x, 20);
			x += 30;
		}
		return sb.toString();
	}

	/**
	 * 画干扰线
	 * 
	 * @param g
	 */
	private static void drawRandomLine(Graphics g) {
		g.setColor(Color.GRAY);
		for (int i = 0; i < 5; i++) {// 画干扰线
			// 生成随机坐标
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * 设置边框
	 * 
	 * @param g
	 */
	private static void setBorder(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, width - 2, height - 2);
	}

	/**
	 * 设置背景色
	 * 
	 * @param g
	 */
	private static void setBackGround(Graphics g) {
		// 设置颜色并填充
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
	}
}
