package com.rkt.common.web;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

/**
 * 生成验证码工具类
 */
public class VerifycodeUtils {
	
	public static final int DEFAULT_LENGTH = 4;
	public static final int WIDTH = 85;
	public static final int HEIGHT = 26;
	public static final String VERIFY_CODE_KEY = "_dl_vd";
	
	private static final String[] fontTypes = {"\u5b8b\u4f53","\u65b0\u5b8b\u4f53","\u9ed1\u4f53","\u6977\u4f53","\u96b6\u4e66"};

    private static final char[] codeSequence = { 'A','a', 'B','b', 'C','c', 'D','d', 'E', 'e','F','f', 'G', 'g', 'H','h',
		'J','j', 'K', 'k', 'M','m', 'N','n', 'P','p', 'Q','q', 'R','r', 'S','s', 'T','t',
		'U','u', 'V', 'v','W', 'w','X', 'x','Y', 'y','Z', 'z','2', '3', '4', '5',
		'6', '7', '8', '9' };
	
	/**
	 * 返回一个长度为length的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomSting(int length) {
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
	
	public static BufferedImage getNextCodeImage(int length) {
		String verifyCode = getRandomSting(length);
		if (length < 1) throw new RuntimeException("length can not be less than 1.");
		return createImage(verifyCode);
	}
	
	public static BufferedImage createCodeImage(String code) {
		return createImage(code);
	}
	
	public static BufferedImage createImage(String code) {
		int x_v = WIDTH / (code.length() + 1);
		int codeY = HEIGHT - 4;
		BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Font font = new Font("Arial", Font.BOLD, 5);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.setColor(Color.YELLOW);
		for (int i = 0; i < 2; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random
					.nextInt(255)).darker());
			g.drawLine(i, HEIGHT / 2 + random.nextInt(3), WIDTH, HEIGHT / 2 + random.nextInt(8));
		}
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		AffineTransform fontAT = new AffineTransform();
		for (int i = 0; i < code.length(); i++) {
			String strRand = new String(new char[] { code.charAt(i) });
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 20 + random.nextInt(6))); 
			// 产生弧度
			int rotate = random.nextInt(7);
			fontAT.rotate(random.nextBoolean() ? Math.toRadians((double)rotate) : - Math.toRadians((double)rotate / 2));
			
			Font fx = new Font(new String[] { "Times New Roman", "Verdana", "Arial" }[random.nextInt(2)], 1, 24).deriveFont(fontAT);
			
			
			g.setFont(fx);
			g.setColor(new Color(red, green, blue).darker());
			g.drawString(strRand, (i) * x_v, codeY);
			randomCode.append(strRand);
		}
		g.dispose();
		return buffImg;
	}
	
	
	public static BufferedImage getNextCodeImage() {
		return getNextCodeImage(VerifycodeUtils.DEFAULT_LENGTH);
	}
	
	/**
	 * 获取图片信息的输入流
	 * 
	 * @param bufferImg
	 * @return
	 * @throws Exception
	 */
	public static InputStream getInputStream(BufferedImage bufferImg) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		ImageIO.write(bufferImg, "JPEG", imageOut);
		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        imageOut.close();
        output.close();
        return input;
	}
}
