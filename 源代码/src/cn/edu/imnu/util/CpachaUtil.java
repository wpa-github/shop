package cn.edu.imnu.util;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
public class CpachaUtil {
	public BufferedImage generatorVCodeImag(HttpServletRequest request){
			int width = 100;
		int height = 30;
		String[] fontNames = new String[]{
				"黑体", "宋体", "Courier", "Arial",
				"Verdana", "Times", "Tahoma", "Georgia"};
		//字体样式
		int[] fontStyles = new int[]{
				Font.BOLD, Font.ITALIC|Font.BOLD
		};
		//1.创建一对象，在内存中图片(验证码图片对象)
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//2.美化图片
		//2.1 填充背景色
		Graphics g = image.getGraphics();//画笔对象
		g.setColor(Color.PINK);//设置画笔颜色
		g.fillRect(0,0,width,height);
		//2.2画边框
		g.setColor(Color.BLUE);
		g.drawRect(0,0,width - 1,height - 1);
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
		//生成随机角标
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 4; i++) {
			int index = ran.nextInt(str.length());
			//获取字符
			char ch = str.charAt(index);//随机字符
			sb.append(ch);
			//2.3写验证码
			Color color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
			g.setColor(color);
			g.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)],23));
			g.drawString(ch+"",width/5*i,height/2+5);
		}
		String checkCode_session = sb.toString();
		//将验证码存入session
		request.getSession().setAttribute("session",checkCode_session);
		//2.4画干扰线
		g.setColor(Color.GREEN);
		//随机生成坐标点
		for (int i = 0; i < 10; i++) {
			int x1 = ran.nextInt(width);
			int x2 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int y2 = ran.nextInt(height);
			Color color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
			g.setColor(color);
			g.drawLine(x1,y1,x2,y2);
		}
		return image;
	}
	
}
