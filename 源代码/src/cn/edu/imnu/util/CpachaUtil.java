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
				"����", "����", "Courier", "Arial",
				"Verdana", "Times", "Tahoma", "Georgia"};
		//������ʽ
		int[] fontStyles = new int[]{
				Font.BOLD, Font.ITALIC|Font.BOLD
		};
		//1.����һ�������ڴ���ͼƬ(��֤��ͼƬ����)
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//2.����ͼƬ
		//2.1 ��䱳��ɫ
		Graphics g = image.getGraphics();//���ʶ���
		g.setColor(Color.PINK);//���û�����ɫ
		g.fillRect(0,0,width,height);
		//2.2���߿�
		g.setColor(Color.BLUE);
		g.drawRect(0,0,width - 1,height - 1);
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
		//��������Ǳ�
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 4; i++) {
			int index = ran.nextInt(str.length());
			//��ȡ�ַ�
			char ch = str.charAt(index);//����ַ�
			sb.append(ch);
			//2.3д��֤��
			Color color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
			g.setColor(color);
			g.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)],23));
			g.drawString(ch+"",width/5*i,height/2+5);
		}
		String checkCode_session = sb.toString();
		//����֤�����session
		request.getSession().setAttribute("session",checkCode_session);
		//2.4��������
		g.setColor(Color.GREEN);
		//������������
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
